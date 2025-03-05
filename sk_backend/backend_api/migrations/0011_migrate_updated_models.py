from django.db import migrations, models
import django.db.models.deletion
from django.conf import settings


def migrate_data(apps, schema_editor):
    # Get model references
    RawMaterial = apps.get_model('backend_api', 'RawMaterial')
    Material = apps.get_model('backend_api', 'Material')

    RecipeHeader = apps.get_model('backend_api', 'RecipeHeader')
    BOMHeader = apps.get_model('backend_api', 'BOMHeader')

    OrderLineItem = apps.get_model('backend_api', 'OrderLineItem')

    RecipeAmount = apps.get_model('backend_api', 'RecipeAmount')
    BOMLine = apps.get_model('backend_api', 'BOMLine')

    InstructionSet = apps.get_model('backend_api', 'InstructionSet')
    BOOHeader = apps.get_model('backend_api', 'BOOHeader')

    Instruction = apps.get_model('backend_api', 'Instruction')
    Operation = apps.get_model('backend_api', 'Operation')

    Batch = apps.get_model('backend_api', 'Batch')
    ProductionOrder = apps.get_model('backend_api', 'ProductionOrder')

    CurrentStock = apps.get_model('backend_api', 'CurrentStock')
    Inventory = apps.get_model('backend_api', 'Inventory')

    # Step 1: Migrate RecipeHeader → BOMHeader
    bom_mapping = {}
    for recipe in RecipeHeader.objects.all():
        bom = BOMHeader.objects.create(
            owner_user_id=recipe.owner_user_id,
            product_name=recipe.recipe_name,
            description=recipe.recipe_description,
            output_quantity=recipe.yield_amount,
            output_uom=recipe.yield_uom,
            insert_user=recipe.insert_user,
            update_user=recipe.update_user,
        )
        bom_mapping[recipe.id] = bom.id

    # Step 2: Migrate RawMaterial → Material
    material_mapping = {}
    for rm in RawMaterial.objects.all():
        bom_header = None
        if hasattr(rm, 'recipe_header_id') and rm.recipe_header_id_id and rm.recipe_header_id_id in bom_mapping:
            bom_header_id = bom_mapping[rm.recipe_header_id_id]
            bom_header = BOMHeader.objects.get(id=bom_header_id)

        material = Material.objects.create(
            description=rm.raw_material_description,
            short_name=rm.raw_material_short_name,
            bom_header=bom_header,
            insert_user=rm.insert_user,
            update_user=rm.update_user,
        )
        material_mapping[rm.id] = material.id

    # Step 3: Update OrderLineItem to point to new Material objects
    for line in OrderLineItem.objects.all():
        if line.raw_material_id in material_mapping:
            material_id = material_mapping[line.raw_material_id]
            line.raw_material_id = material_id
            line.save()

    # Step 4: Migrate RecipeAmount → BOMLine
    bomline_mapping = {}
    for r_a in RecipeAmount.objects.all():
        if r_a.recipe_header_id in bom_mapping and r_a.raw_material_id in material_mapping:
            bom_header = BOMHeader.objects.get(id=bom_mapping[r_a.recipe_header_id])
            material = Material.objects.get(id=material_mapping[r_a.raw_material_id])

            bomline = BOMLine.objects.create(
                bom_header=bom_header,
                material=material,
                quantity=r_a.recipe_amount,
                uom=r_a.recipe_amount_uom,
                insert_user=r_a.insert_user,
                update_user=r_a.update_user
            )
            bomline_mapping[r_a.id] = bomline.id

    # Step 5: Migrate InstructionSet → BOOHeader
    boo_header_mapping = {}
    for i_s in InstructionSet.objects.all():
        if i_s.recipe_header_id in bom_mapping:
            bom_header = BOMHeader.objects.get(id=bom_mapping[i_s.recipe_header_id])
            booheader = BOOHeader.objects.create(
                bom_header=bom_header,
                insert_user=i_s.insert_user,
                update_user=i_s.update_user
            )
            boo_header_mapping[i_s.id] = booheader.id

    # Step 6: Migrate Instruction → Operation
    operation_mapping = {}
    for instr in Instruction.objects.all():
        if instr.instruction_set_id in boo_header_mapping:
            boo_header = BOOHeader.objects.get(id=boo_header_mapping[instr.instruction_set_id])
            operation = Operation.objects.create(
                boo_header=boo_header,
                description=instr.instruction_text,
                sequence_number=instr.instruction_sequence_number,
                estimated_time=1.0,  # Default value
                time_uom='minutes',  # Default value
                insert_user=instr.insert_user,
                update_user=instr.update_user
            )
            operation_mapping[instr.id] = operation.id

    # Step 7: Migrate Batch → ProductionOrder
    production_order_mapping = {}
    for b in Batch.objects.all():
        if b.recipe_header_id in bom_mapping:
            bom_header = BOMHeader.objects.get(id=bom_mapping[b.recipe_header_id])
            production_order = ProductionOrder.objects.create(
                owner_user_id=b.owner_user_id,
                bom_header=bom_header,
                production_date=b.batch_date,
                quantity=b.yield_count,
                status='completed',  # Default for existing batches
                insert_user=b.insert_user,
                update_user=b.update_user
            )
            production_order_mapping[b.id] = production_order.id

    # Step 8: Migrate CurrentStock → Inventory
    inventory_mapping = {}
    for cs in CurrentStock.objects.all():
        if hasattr(cs, 'batch') and cs.batch_id is not None and cs.batch_id in production_order_mapping:
            production_order = ProductionOrder.objects.get(id=production_order_mapping[cs.batch_id])

            # Find a suitable material - this is just a basic approach
            # You might need to adjust this based on your specific data relationships
            default_material = Material.objects.first()

            if default_material:
                inventory = Inventory.objects.create(
                    owner_user_id=cs.owner_user_id,
                    material=default_material,
                    quantity=1.0,  # Default quantity
                    uom='UNIT',    # Default UOM
                    production_order=production_order,
                    location='',   # Default location
                    insert_user=cs.insert_user,
                    update_user=cs.update_user
                )
                inventory_mapping[cs.id] = inventory.id


class Migration(migrations.Migration):
    dependencies = [
        ('backend_api', '0010_unitconversion_alter_rawmaterial_recipe_header_id'),
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
    ]

    operations = [
        # Step 1: Create new models
        migrations.CreateModel(
            name='BOMHeader',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('insert_dttm', models.DateTimeField(auto_now_add=True)),
                ('update_dttm', models.DateTimeField(auto_now=True)),
                ('product_name', models.CharField(max_length=64)),
                ('description', models.CharField(max_length=256)),
                ('output_quantity', models.DecimalField(decimal_places=4, max_digits=12)),
                ('output_uom', models.CharField(
                    choices=[('C', 'Cup'), ('CL', 'Centiliter'), ('CM', 'Centimeter'), ('DL', 'Deciliter'),
                             ('FLOZ', 'Fluid Ounce (volume)'), ('G', 'Gram'), ('IN', 'Inch'), ('KG', 'Kilogram'),
                             ('L', 'Liter'), ('LB', 'Pound'), ('M', 'Meter'), ('MG', 'Milligram'), ('ML', 'Milliliter'),
                             ('MM', 'Millimeter'), ('OZ', 'Dry Ounce (weight)'), ('PC', 'Piece'),
                             ('TBSP', 'Tablespoon'), ('THOU', 'Thousandth-inch'), ('TSP', 'Teaspoon'), ('UNIT', 'Unit'),
                             ('YD', 'Yard')], max_length=4)),
                ('insert_user', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='+',
                                                  to=settings.AUTH_USER_MODEL)),
                ('owner_user_id',
                 models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL)),
                ('update_user', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='+',
                                                  to=settings.AUTH_USER_MODEL)),
            ],
            options={
                'abstract': False,
            },
        ),
        # Create Material model
        migrations.CreateModel(
            name='Material',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('insert_dttm', models.DateTimeField(auto_now_add=True)),
                ('update_dttm', models.DateTimeField(auto_now=True)),
                ('description', models.CharField(max_length=512)),
                ('short_name', models.CharField(max_length=64)),
                ('bom_header', models.ForeignKey(blank=True,
                                                 help_text='Link to the Bill of Materials if this item is manufactured internally.',
                                                 null=True, on_delete=django.db.models.deletion.CASCADE,
                                                 to='backend_api.bomheader')),
                ('insert_user', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='+',
                                                  to=settings.AUTH_USER_MODEL)),
                ('update_user', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='+',
                                                  to=settings.AUTH_USER_MODEL)),
            ],
            options={
                'abstract': False,
            },
        ),

        migrations.CreateModel(
            name='BOOHeader',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('insert_dttm', models.DateTimeField(auto_now_add=True)),
                ('update_dttm', models.DateTimeField(auto_now=True)),
                ('bom_header',
                 models.OneToOneField(on_delete=django.db.models.deletion.CASCADE, to='backend_api.bomheader')),
                ('insert_user', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='+',
                                                  to=settings.AUTH_USER_MODEL)),
                ('update_user', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='+',
                                                  to=settings.AUTH_USER_MODEL)),
            ],
            options={
                'abstract': False,
            },
        ),
        migrations.CreateModel(
            name='BOMLine',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('insert_dttm', models.DateTimeField(auto_now_add=True)),
                ('update_dttm', models.DateTimeField(auto_now=True)),
                ('quantity', models.DecimalField(decimal_places=6, max_digits=14)),
                ('uom', models.CharField(
                    choices=[('C', 'Cup'), ('CL', 'Centiliter'), ('CM', 'Centimeter'), ('DL', 'Deciliter'),
                             ('FLOZ', 'Fluid Ounce (volume)'), ('G', 'Gram'), ('IN', 'Inch'), ('KG', 'Kilogram'),
                             ('L', 'Liter'), ('LB', 'Pound'), ('M', 'Meter'), ('MG', 'Milligram'), ('ML', 'Milliliter'),
                             ('MM', 'Millimeter'), ('OZ', 'Dry Ounce (weight)'), ('PC', 'Piece'),
                             ('TBSP', 'Tablespoon'), ('THOU', 'Thousandth-inch'), ('TSP', 'Teaspoon'), ('UNIT', 'Unit'),
                             ('YD', 'Yard')], max_length=4)),
                ('bom_header',
                 models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='backend_api.bomheader')),
                ('insert_user', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='+',
                                                  to=settings.AUTH_USER_MODEL)),
                ('update_user', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='+',
                                                  to=settings.AUTH_USER_MODEL)),
                ('material', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='backend_api.material')),
            ],
            options={
                'abstract': False,
            },
        ),
        migrations.CreateModel(
            name='Operation',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('insert_dttm', models.DateTimeField(auto_now_add=True)),
                ('update_dttm', models.DateTimeField(auto_now=True)),
                ('description', models.CharField(max_length=3000)),
                ('sequence_number', models.PositiveSmallIntegerField()),
                ('estimated_time', models.DecimalField(blank=True, decimal_places=2, max_digits=10, null=True)),
                ('time_uom', models.CharField(blank=True, max_length=10, null=True)),
                ('boo_header',
                 models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='backend_api.booheader')),
                ('insert_user', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='+',
                                                  to=settings.AUTH_USER_MODEL)),
                ('update_user', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='+',
                                                  to=settings.AUTH_USER_MODEL)),
            ],
            options={
                'ordering': ['sequence_number'],
            },
        ),
        migrations.CreateModel(
            name='ProductionOrder',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('insert_dttm', models.DateTimeField(auto_now_add=True)),
                ('update_dttm', models.DateTimeField(auto_now=True)),
                ('production_date', models.DateField()),
                ('quantity', models.PositiveSmallIntegerField()),
                ('status', models.CharField(
                    choices=[('planned', 'Planned'), ('in_progress', 'In Progress'), ('completed', 'Completed'),
                             ('cancelled', 'Cancelled')], default='planned', max_length=20)),
                ('bom_header',
                 models.ForeignKey(on_delete=django.db.models.deletion.RESTRICT, to='backend_api.bomheader')),
                ('insert_user', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='+',
                                                  to=settings.AUTH_USER_MODEL)),
                ('owner_user_id',
                 models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL)),
                ('update_user', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='+',
                                                  to=settings.AUTH_USER_MODEL)),
            ],
            options={
                'abstract': False,
            },
        ),
        migrations.CreateModel(
            name='Inventory',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('insert_dttm', models.DateTimeField(auto_now_add=True)),
                ('update_dttm', models.DateTimeField(auto_now=True)),
                ('quantity', models.DecimalField(decimal_places=6, max_digits=14)),
                ('uom', models.CharField(
                    choices=[('C', 'Cup'), ('CL', 'Centiliter'), ('CM', 'Centimeter'), ('DL', 'Deciliter'),
                             ('FLOZ', 'Fluid Ounce (volume)'), ('G', 'Gram'), ('IN', 'Inch'), ('KG', 'Kilogram'),
                             ('L', 'Liter'), ('LB', 'Pound'), ('M', 'Meter'), ('MG', 'Milligram'), ('ML', 'Milliliter'),
                             ('MM', 'Millimeter'), ('OZ', 'Dry Ounce (weight)'), ('PC', 'Piece'),
                             ('TBSP', 'Tablespoon'), ('THOU', 'Thousandth-inch'), ('TSP', 'Teaspoon'), ('UNIT', 'Unit'),
                             ('YD', 'Yard')], max_length=4)),
                ('location', models.CharField(blank=True, max_length=50)),
                ('insert_user', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='+',
                                                  to=settings.AUTH_USER_MODEL)),
                ('owner_user_id',
                 models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL)),
                ('update_user', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, related_name='+',
                                                  to=settings.AUTH_USER_MODEL)),
                ('material', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='backend_api.material')),
                ('production_order',
                 models.ForeignKey(blank=True, null=True, on_delete=django.db.models.deletion.RESTRICT,
                                   to='backend_api.productionorder')),
            ],
            options={
                'abstract': False,
            },
        ),
        # Step 2: Run data migration to copy data from old models to new
        migrations.RunPython(migrate_data),

        # Step 3: Add a temporary field for Material in OrderLineItem
        migrations.AddField(
            model_name='orderlineitem',
            name='material',
            field=models.ForeignKey(null=True, on_delete=django.db.models.deletion.PROTECT, to='backend_api.material'),
        ),

        # Step 4: Move data from raw_material to material in OrderLineItem
        migrations.RunSQL(
            "UPDATE backend_api_orderlineitem SET material_id = raw_material_id;",
            "UPDATE backend_api_orderlineitem SET raw_material_id = material_id;"
        ),

        # Step 5: Remove null constraint on material field
        migrations.AlterField(
            model_name='orderlineitem',
            name='material',
            field=models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='backend_api.material'),
        ),

        # Step 6: Remove old raw_material field
        migrations.RemoveField(
            model_name='orderlineitem',
            name='raw_material',
        ),

        # Step 7: Delete old models after data migration
        migrations.DeleteModel(name='RecipeHeader'),
        migrations.DeleteModel(name='RawMaterial'),
        migrations.DeleteModel(name='Batch'),
        migrations.DeleteModel(name='CurrentStock'),
        migrations.DeleteModel(name='Instruction'),
        migrations.DeleteModel(name='InstructionSet'),
        migrations.DeleteModel(name='RecipeAmount'),
        migrations.DeleteModel(name='RecipeItem'),
    ]