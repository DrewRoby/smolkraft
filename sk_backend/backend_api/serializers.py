from rest_framework import serializers
from backend_api.models import Vendor, OrderHeader, OrderLineItem, RawMaterial, RecipeHeader, RecipeCost


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
