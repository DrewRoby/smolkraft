from django.db import models
from django.conf import settings


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

    class Meta:
        constraints = [
            models.UniqueConstraint(fields=['owner_user_id', 'cost_total', 'vendor'], name='unique_order_header_per_user_vendor_cost')
        ]


class RawMaterial(BaseTable):
    raw_material_description = models.CharField(max_length=512)
    raw_material_short_name = models.CharField(max_length=64)


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


class RecipeHeader(BaseTable):
    owner_user_id = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE)
    recipe_name = models.CharField(max_length=64)
    recipe_description = models.CharField(max_length=256)
    yield_amount = models.DecimalField(max_digits=12, decimal_places=4)
    yield_uom = models.CharField(max_length=4, choices=UNITS_OF_MEASURE)


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


class RecipeCost(BaseTable):
    recipe_header = models.ForeignKey(RecipeHeader, on_delete=models.CASCADE)
    raw_material = models.ForeignKey(RawMaterial, on_delete=models.PROTECT)
    recipe_amount = models.DecimalField(max_digits=12, decimal_places=4)
    recipe_uom = models.CharField(max_length=4, choices=UNITS_OF_MEASURE)


class CurrentStock(BaseTable):
    owner_user_id = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE)
    batch = models.ForeignKey(Batch, on_delete=models.RESTRICT)

