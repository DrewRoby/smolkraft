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
    BomLine = apps.get_model('backend_api', 'BOMLine')

    InstructionSet = apps.get_model('backend_api', 'InstructionSet')
    BooHeader = apps.get_model('backend_api', 'BooHeader')

    Instruction = apps.get_model('backend_api', 'Instruction')
    Operation = apps.get_model('backend_api', 'Operation')

    Batch = apps.get_model('backend_api', '')
    ProductionOrder = apps.get_model('backend_api', '')

    CurrentStock = apps.get_model('backend_api', '')
    Inventory = apps.get_model('backend_api', '')

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

    bomline_mapping = {}
    for r_a in RecipeAmount.objects.all():
        bomline = BomLine.objects.create(
            bom_header=r_a.recipe_header,
            material=r_a.raw_material,
            quantity=r_a.recipe_amount,
            uom=r_a.recipe_amount_uom
        )
        bomline_mapping[r_a.id] = bomline[id]

    boo_header_mapping = {}
    for i_s in InstructionSet.objects.all():
        booheader = BooHeader.objects.create(
            bom_header=i_s.recipe_header
        )
        boo_header_mapping[i_s.id] = booheader[id]

    operation_mapping = {}
    for instr in Instruction.objects.all():
        operation = Operation.objects.create(
            boo_header=instr.instruction_set,
            description=instr.instruction_text,
            sequence_number=instr.instruction_sequence_number,
            estimated_time=1.0,
            time_uom='seconds'
        )
        operation_mapping[instr.id] = operation[id]

    production_order_mapping = {}
    for b in Batch.objects.all():
        production_order = ProductionOrder.objects.create(
            owner_user_id=b.owner_user_id,
            bom_header=b.recipe_header,
            production_date=b.batch_date,
            quantity=b.yield_count,
            status=''
        )
        production_order_mapping[b.id] = production_order[id]

    inventory_mapping = {}
    for cs in CurrentStock.objects.all():
        inventory = Inventory.objects.create(
            owner_user_id=cs.owner_user_id
        )
        inventory_mapping[cs.id] = inventory[id]

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
        # Remove all your old models here
        migrations.DeleteModel(name='RecipeHeader'),
        migrations.DeleteModel(name='RawMaterial'),
        migrations.DeleteModel(
            name='Batch',
        ),
        migrations.DeleteModel(
            name='CurrentStock',
        ),
        migrations.DeleteModel(
            name='Instruction',
        ),
        migrations.DeleteModel(
            name='InstructionSet',
        ),
        migrations.DeleteModel(
            name='RecipeAmount',
        ),
        migrations.DeleteModel(
            name='RecipeItem',
        ),
        migrations.DeleteModel(
            name='InstructionSet',
        ),
        migrations.DeleteModel(
            name='Instruction',
        ),
        migrations.DeleteModel(
            name='Batch',
        ),
        migrations.DeleteModel(
            name='CurrentStock',
        )

    ]
