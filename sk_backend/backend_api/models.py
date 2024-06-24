from django.db import models

UNITS_OF_MEASURE = {
    'PC': 'Piece',
    'UNIT': 'Unit',
    'LB': 'Pound',
    'OZ': 'Ounce',
    'FLOZ': 'Fluid Ounce',
    'YD': 'Yard',
    'IN': 'Inch',
    'THOU': 'Thousandth-inch',
    'G': 'Gram',
    'KG': 'Kilogram',
    'MG': 'Milligram',
    'M': 'Meter',
    'CM': 'Centimeter',
    'MM': 'Millimeter',
    'L': 'Liter',
    'ML': 'Milliliter',
    'CL': 'Centiliter',
    'DL': 'Deciliter',
}


class BaseTable(models.Model):
    insert_user = models.CharField(max_length=128)
    insert_dttm = models.DateTimeField(auto_now_add=True)
    update_user = models.CharField(max_length=128)
    update_dttm = models.DateTimeField(auto_now=True)

    class Meta:
        abstract = True


class Vendor(BaseTable):
    vendor_id = models.IntegerField(primary_key=True)
    vendor_name = models.CharField(max_length=256)


class OrderHeader(BaseTable):
    order_header_id = models.IntegerField(primary_key=True)
    cost_subtotal = models.DecimalField(max_digits=10, decimal_places=2)
    cost_tax = models.DecimalField(max_digits=10, decimal_places=2)
    cost_shipping = models.DecimalField(max_digits=10, decimal_places=2)
    cost_discount = models.DecimalField(max_digits=10, decimal_places=2)
    cost_total = models.DecimalField(max_digits=10, decimal_places=2)
    item_count = models.IntegerField()
    vendor_id = models.ForeignKey(Vendor, on_delete=models.PROTECT)
    vendor_order_id = models.CharField(max_length=128)
    order_date = models.DateField()


class RawMaterial(BaseTable):
    raw_material_id = models.IntegerField(primary_key=True)
    raw_material_description = models.CharField(max_length=512)
    raw_material_short_name = models.CharField(max_length=64)


class OrderLineItem(BaseTable):
    order_line_item_id = models.IntegerField(primary_key=True)
    order_header_id = models.ForeignKey(OrderHeader, on_delete=models.PROTECT)
    raw_material_id = models.ForeignKey(RawMaterial, on_delete=models.PROTECT)
    item_count = models.IntegerField()
    sku = models.CharField(max_length=128)
    upc = models.CharField(max_length=128)
    unit_price = models.DecimalField(max_digits=10, decimal_places=2)
    unit_amount = models.DecimalField(max_digits=12, decimal_places=4)
    unit_uom = models.CharField(max_length=4, choices=UNITS_OF_MEASURE)


class RecipeHeader(BaseTable):
    recipe_header_id = models.IntegerField(primary_key=True)
    recipe_name = models.CharField(max_length=64)
    recipe_description = models.CharField(max_length=256)
    yield_amount = models.DecimalField(max_digits=12, decimal_places=4)
    yield_uom = models.CharField(max_length=4, choices=UNITS_OF_MEASURE)


class RecipeCost(BaseTable):
    recipe_cost_id = models.IntegerField(primary_key=True)
    recipe_header_id = models.ForeignKey(RecipeHeader, on_delete=models.CASCADE)
    raw_material_id = models.ForeignKey(RawMaterial, on_delete=models.PROTECT)
    recipe_amount = models.DecimalField(max_digits=12, decimal_places=4)
    recipe_uom = models.CharField(max_length=4, choices=UNITS_OF_MEASURE)

