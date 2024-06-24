# Generated by Django 5.0.6 on 2024-06-24 16:20

import django.db.models.deletion
from django.conf import settings
from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
    ]

    operations = [
        migrations.CreateModel(
            name='InstructionSet',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('insert_user', models.CharField(max_length=128)),
                ('insert_dttm', models.DateTimeField(auto_now_add=True)),
                ('update_user', models.CharField(max_length=128)),
                ('update_dttm', models.DateTimeField(auto_now=True)),
            ],
            options={
                'abstract': False,
            },
        ),
        migrations.CreateModel(
            name='OrderHeader',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('insert_user', models.CharField(max_length=128)),
                ('insert_dttm', models.DateTimeField(auto_now_add=True)),
                ('update_user', models.CharField(max_length=128)),
                ('update_dttm', models.DateTimeField(auto_now=True)),
                ('cost_subtotal', models.DecimalField(decimal_places=2, max_digits=10)),
                ('cost_tax', models.DecimalField(decimal_places=2, max_digits=10)),
                ('cost_shipping', models.DecimalField(decimal_places=2, max_digits=10)),
                ('cost_discount', models.DecimalField(decimal_places=2, max_digits=10)),
                ('cost_total', models.DecimalField(decimal_places=2, max_digits=10)),
                ('item_count', models.IntegerField()),
                ('vendor_order_id', models.CharField(max_length=128)),
                ('order_date', models.DateField()),
            ],
            options={
                'abstract': False,
            },
        ),
        migrations.CreateModel(
            name='RawMaterial',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('insert_user', models.CharField(max_length=128)),
                ('insert_dttm', models.DateTimeField(auto_now_add=True)),
                ('update_user', models.CharField(max_length=128)),
                ('update_dttm', models.DateTimeField(auto_now=True)),
                ('raw_material_description', models.CharField(max_length=512)),
                ('raw_material_short_name', models.CharField(max_length=64)),
            ],
            options={
                'abstract': False,
            },
        ),
        migrations.CreateModel(
            name='Vendor',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('insert_user', models.CharField(max_length=128)),
                ('insert_dttm', models.DateTimeField(auto_now_add=True)),
                ('update_user', models.CharField(max_length=128)),
                ('update_dttm', models.DateTimeField(auto_now=True)),
                ('vendor_name', models.CharField(max_length=256)),
            ],
            options={
                'abstract': False,
            },
        ),
        migrations.CreateModel(
            name='Batch',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('insert_user', models.CharField(max_length=128)),
                ('insert_dttm', models.DateTimeField(auto_now_add=True)),
                ('update_user', models.CharField(max_length=128)),
                ('update_dttm', models.DateTimeField(auto_now=True)),
                ('batch_date', models.DateField()),
                ('yield_count', models.PositiveSmallIntegerField()),
                ('owner_user_id', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL)),
            ],
            options={
                'abstract': False,
            },
        ),
        migrations.CreateModel(
            name='CurrentStock',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('insert_user', models.CharField(max_length=128)),
                ('insert_dttm', models.DateTimeField(auto_now_add=True)),
                ('update_user', models.CharField(max_length=128)),
                ('update_dttm', models.DateTimeField(auto_now=True)),
                ('batch', models.ForeignKey(on_delete=django.db.models.deletion.RESTRICT, to='backend_api.batch')),
                ('owner_user_id', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL)),
            ],
            options={
                'abstract': False,
            },
        ),
        migrations.CreateModel(
            name='Instruction',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('insert_user', models.CharField(max_length=128)),
                ('insert_dttm', models.DateTimeField(auto_now_add=True)),
                ('update_user', models.CharField(max_length=128)),
                ('update_dttm', models.DateTimeField(auto_now=True)),
                ('instruction_text', models.CharField(max_length=3000)),
                ('instruction_sequence_number', models.PositiveSmallIntegerField()),
                ('instruction_set', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='backend_api.instructionset')),
            ],
            options={
                'abstract': False,
            },
        ),
        migrations.CreateModel(
            name='OrderLineItem',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('insert_user', models.CharField(max_length=128)),
                ('insert_dttm', models.DateTimeField(auto_now_add=True)),
                ('update_user', models.CharField(max_length=128)),
                ('update_dttm', models.DateTimeField(auto_now=True)),
                ('item_count', models.IntegerField()),
                ('sku', models.CharField(max_length=128)),
                ('upc', models.CharField(max_length=128)),
                ('unit_price', models.DecimalField(decimal_places=2, max_digits=10)),
                ('unit_amount', models.DecimalField(decimal_places=4, max_digits=12)),
                ('unit_uom', models.CharField(choices=[('PC', 'Piece'), ('UNIT', 'Unit'), ('LB', 'Pound'), ('OZ', 'Ounce'), ('FLOZ', 'Fluid Ounce'), ('YD', 'Yard'), ('IN', 'Inch'), ('THOU', 'Thousandth-inch'), ('G', 'Gram'), ('KG', 'Kilogram'), ('MG', 'Milligram'), ('M', 'Meter'), ('CM', 'Centimeter'), ('MM', 'Millimeter'), ('L', 'Liter'), ('ML', 'Milliliter'), ('CL', 'Centiliter'), ('DL', 'Deciliter')], max_length=4)),
                ('order_header', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='backend_api.orderheader')),
                ('raw_material', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='backend_api.rawmaterial')),
            ],
            options={
                'abstract': False,
            },
        ),
        migrations.CreateModel(
            name='RecipeHeader',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('insert_user', models.CharField(max_length=128)),
                ('insert_dttm', models.DateTimeField(auto_now_add=True)),
                ('update_user', models.CharField(max_length=128)),
                ('update_dttm', models.DateTimeField(auto_now=True)),
                ('recipe_name', models.CharField(max_length=64)),
                ('recipe_description', models.CharField(max_length=256)),
                ('yield_amount', models.DecimalField(decimal_places=4, max_digits=12)),
                ('yield_uom', models.CharField(choices=[('PC', 'Piece'), ('UNIT', 'Unit'), ('LB', 'Pound'), ('OZ', 'Ounce'), ('FLOZ', 'Fluid Ounce'), ('YD', 'Yard'), ('IN', 'Inch'), ('THOU', 'Thousandth-inch'), ('G', 'Gram'), ('KG', 'Kilogram'), ('MG', 'Milligram'), ('M', 'Meter'), ('CM', 'Centimeter'), ('MM', 'Millimeter'), ('L', 'Liter'), ('ML', 'Milliliter'), ('CL', 'Centiliter'), ('DL', 'Deciliter')], max_length=4)),
                ('owner_user_id', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL)),
            ],
            options={
                'abstract': False,
            },
        ),
        migrations.CreateModel(
            name='RecipeCost',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('insert_user', models.CharField(max_length=128)),
                ('insert_dttm', models.DateTimeField(auto_now_add=True)),
                ('update_user', models.CharField(max_length=128)),
                ('update_dttm', models.DateTimeField(auto_now=True)),
                ('recipe_amount', models.DecimalField(decimal_places=4, max_digits=12)),
                ('recipe_uom', models.CharField(choices=[('PC', 'Piece'), ('UNIT', 'Unit'), ('LB', 'Pound'), ('OZ', 'Ounce'), ('FLOZ', 'Fluid Ounce'), ('YD', 'Yard'), ('IN', 'Inch'), ('THOU', 'Thousandth-inch'), ('G', 'Gram'), ('KG', 'Kilogram'), ('MG', 'Milligram'), ('M', 'Meter'), ('CM', 'Centimeter'), ('MM', 'Millimeter'), ('L', 'Liter'), ('ML', 'Milliliter'), ('CL', 'Centiliter'), ('DL', 'Deciliter')], max_length=4)),
                ('raw_material', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='backend_api.rawmaterial')),
                ('recipe_header', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='backend_api.recipeheader')),
            ],
            options={
                'abstract': False,
            },
        ),
        migrations.AddField(
            model_name='instructionset',
            name='recipe_header',
            field=models.OneToOneField(on_delete=django.db.models.deletion.CASCADE, to='backend_api.recipeheader'),
        ),
        migrations.AddField(
            model_name='batch',
            name='recipe_header',
            field=models.ForeignKey(on_delete=django.db.models.deletion.RESTRICT, to='backend_api.recipeheader'),
        ),
        migrations.CreateModel(
            name='RecipeItem',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('insert_user', models.CharField(max_length=128)),
                ('insert_dttm', models.DateTimeField(auto_now_add=True)),
                ('update_user', models.CharField(max_length=128)),
                ('update_dttm', models.DateTimeField(auto_now=True)),
                ('raw_material', models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='backend_api.rawmaterial')),
                ('recipe_header', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='backend_api.recipeheader')),
            ],
            options={
                'abstract': False,
            },
        ),
        migrations.AddField(
            model_name='orderheader',
            name='vendor',
            field=models.ForeignKey(on_delete=django.db.models.deletion.PROTECT, to='backend_api.vendor'),
        ),
    ]
