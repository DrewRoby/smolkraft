from django.db import models
from django.conf import settings


UNITS_OF_MEASURE = {
    'C': 'Cup',
    'CL': 'Centiliter',
    'CM': 'Centimeter',
    'DL': 'Deciliter',
    'FLOZ': 'Fluid Ounce (volume)',
    'G': 'Gram',
    'IN': 'Inch',
    'KG': 'Kilogram',
    'L': 'Liter',
    'LB': 'Pound',
    'M': 'Meter',
    'MG': 'Milligram',
    'ML': 'Milliliter',
    'MM': 'Millimeter',
    'OZ': 'Dry Ounce (weight)',
    'PC': 'Piece',
    'TBSP': 'Tablespoon',
    'THOU': 'Thousandth-inch',
    'TSP': 'Teaspoon',
    'UNIT': 'Unit',
    'YD': 'Yard',
}


class UnitConversion(models.Model):
    from_unit_name = models.CharField(max_length=32)
    to_unit_name = models.CharField(max_length=32)
    multiply_by = models.DecimalField(max_digits=16, decimal_places=10)

class BaseTable(models.Model):
    # insert_user = models.CharField(max_length=128)
    #NOTE: related_name='+' disables reversing for this field to avoid reverse accessor clashes.
    # https://docs.djangoproject.com/en/3.2/ref/models/fields/#django.db.models.ForeignKey.related_name
    # https://docs.djangoproject.com/en/3.2/topics/db/queries/#backwards-related-objects
    # Remember this in case everything goes tits up when you start trying to access information for a given user.
    insert_user = models.ForeignKey(settings.AUTH_USER_MODEL, related_name='+', on_delete=models.CASCADE)
    insert_dttm = models.DateTimeField(auto_now_add=True)
    # update_user = models.CharField(max_length=128)
    update_user = models.ForeignKey(settings.AUTH_USER_MODEL, related_name='+', on_delete=models.CASCADE)
    update_dttm = models.DateTimeField(auto_now=True)

    class Meta:
        abstract = True


class Vendor(BaseTable):
    vendor_name = models.CharField(max_length=256)


class OrderHeader(BaseTable):
    owner_user_id = models.ForeignKey(settings.AUTH_USER_MODEL, related_name='+', on_delete=models.CASCADE)
    cost_subtotal = models.DecimalField(max_digits=10, decimal_places=2)
    cost_tax = models.DecimalField(max_digits=10, decimal_places=2)
    cost_shipping = models.DecimalField(max_digits=10, decimal_places=2)
    cost_discount = models.DecimalField(max_digits=10, decimal_places=2)
    cost_total = models.DecimalField(max_digits=10, decimal_places=2)
    item_count = models.IntegerField()
    vendor = models.ForeignKey(Vendor, on_delete=models.PROTECT)
    vendor_order_id = models.CharField(max_length=128)
    order_date = models.DateField()

# Unique compound key constraint
    class Meta:
        constraints = [
            models.UniqueConstraint(fields=['owner_user_id', 'vendor', 'cost_total'],
                                    name='unique_order_header_per_user_vendor_cost')
        ]


class RecipeHeader(BaseTable):
    owner_user_id = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE)
    recipe_name = models.CharField(max_length=64)
    recipe_description = models.CharField(max_length=256)
    yield_amount = models.DecimalField(max_digits=12, decimal_places=4)
    yield_uom = models.CharField(max_length=4, choices=UNITS_OF_MEASURE)


class RawMaterial(BaseTable):
    raw_material_description = models.CharField(max_length=512)
    raw_material_short_name = models.CharField(max_length=64)
    recipe_header_id = models.ForeignKey(RecipeHeader, on_delete=models.CASCADE, null=True,
                                         help_text="Use this field if this raw material is made from one of your recipes (e.g. frosting for a cake).")

class RawishMaterial(RawMaterial):
    def __str__(self):
        return f"{ self.raw_material_short_name }, { self.recipe_header_id }"
``

class OrderLineItem(BaseTable):
    owner_user_id = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE)
    order_header = models.ForeignKey(OrderHeader, on_delete=models.PROTECT)
    raw_material = models.ForeignKey(RawMaterial, on_delete=models.PROTECT)
    item_count = models.IntegerField()
    sku = models.CharField(max_length=128, blank=True)
    upc = models.CharField(max_length=128, blank=True)
    unit_price = models.DecimalField(max_digits=10, decimal_places=2)
    unit_amount = models.DecimalField(max_digits=12, decimal_places=4)
    unit_uom = models.CharField(max_length=4, choices=UNITS_OF_MEASURE)


class InstructionSet(BaseTable):
    recipe_header = models.OneToOneField(RecipeHeader, on_delete=models.CASCADE)


class Instruction(BaseTable):
    instruction_set = models.ForeignKey(InstructionSet, on_delete=models.CASCADE)
    instruction_text = models.CharField(max_length=3000)
    instruction_sequence_number = models.PositiveSmallIntegerField()


class RecipeItem(BaseTable):
    recipe_header = models.ForeignKey(RecipeHeader, on_delete=models.CASCADE)
    raw_material = models.ForeignKey(RawMaterial, on_delete=models.PROTECT)


class Batch(BaseTable):
    owner_user_id = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE)
    recipe_header = models.ForeignKey(RecipeHeader, on_delete=models.RESTRICT)
    batch_date = models.DateField()
    yield_count = models.PositiveSmallIntegerField()


class RecipeAmount(BaseTable):
    recipe_header = models.ForeignKey(RecipeHeader, on_delete=models.CASCADE)
    raw_material = models.ForeignKey(RawMaterial, on_delete=models.PROTECT)
    recipe_amount = models.DecimalField(max_digits=14, decimal_places=6)
    recipe_amount_uom = models.CharField(max_length=4, choices=UNITS_OF_MEASURE)


class CurrentStock(BaseTable):
    owner_user_id = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE)
    batch = models.ForeignKey(Batch, on_delete=models.RESTRICT)


