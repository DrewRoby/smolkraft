from rest_framework import serializers
from backend_api.models import Vendor, OrderHeader, OrderLineItem, RawMaterial, RecipeHeader, RecipeAmount, Batch, RecipeItem, Instruction, InstructionSet, CurrentStock


# class DynamicFieldsModelSerializer(serializers.ModelSerializer):
#     """
#     A ModelSerializer that takes an additional `fields` argument that
#     controls which fields should be displayed.
#     """
#
#     def __init__(self, *args, **kwargs):
#         # Don't pass the 'fields' arg up to the superclass
#         fields = kwargs.pop('fields', None)
#
#         # Instantiate the superclass normally
#         super(DynamicFieldsModelSerializer, self).__init__(*args, **kwargs)
#
#         if fields is not None:
#             # Drop any fields that are not specified in the `fields` argument.
#             allowed = set(fields)
#             existing = set(self.fields.keys())
#             for field_name in existing - allowed:
#                 self.fields.pop(field_name)


class VendorSerializer(serializers.ModelSerializer):
    class Meta:
        model = Vendor
        fields = '__all__'


class OrderHeaderSerializer(serializers.ModelSerializer):
    class Meta:
        model = OrderHeader
        fields = '__all__'


class OrderLineItemSerializer(serializers.ModelSerializer):
    class Meta:
        model = OrderLineItem
        fields = '__all__'


class RawMaterialSerializer(serializers.ModelSerializer):
    class Meta:
        model = RawMaterial
        fields = '__all__'


class RecipeHeaderSerializer(serializers.ModelSerializer):
    class Meta:
        model = RecipeHeader
        fields = '__all__'


# class IngredientNameSubSer(serializers.ModelSerializer):
#     class Meta:
#         model = RawMaterial
#         fields = ['raw_materal_short_name']

class RecipeAmountSerializer(serializers.ModelSerializer):
    ingredients = RawMaterialSerializer(many=True, read_only=True)

    # ingredient_name = serializers.SlugRelatedField(
    #     many=False,
    #     read_only=True,
    #     slug_field='raw_material'
    # )

    class Meta:
        model = RecipeAmount
        fields = ['id', 'recipe_amount', 'recipe_amount_uom', 'ingredients']
        # fields = '__all__'


class CurrentStockSerializer(serializers.ModelSerializer):
    class Meta:
        model = CurrentStock
        fields = '__all__'


class BatchSerializer(serializers.ModelSerializer):
    class Meta:
        model = Batch
        fields = '__all__'


class RecipeItemSerializer(serializers.ModelSerializer):
    class Meta:
        model = RecipeItem
        fields = '__all__'


class InstructionSerializer(serializers.ModelSerializer):
    class Meta:
        model = Instruction
        fields = '__all__'


class InstructionSetSerializer(serializers.ModelSerializer):
    class Meta:
        model = InstructionSet
        fields = '__all__'
