from rest_framework import serializers
from backend_api.models import Vendor, OrderHeader, OrderLineItem, RawMaterial, RecipeHeader, RecipeCost, Batch, RecipeItem, Instruction, InstructionSet, CurrentStock


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


class RecipeCostSerializer(serializers.ModelSerializer):
    class Meta:
        model = RecipeCost
        fields = '__all__'


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
