from django.shortcuts import render
from backend_api.models import Vendor, OrderHeader, OrderLineItem, RawMaterial, RecipeHeader, RecipeAmount
from backend_api.serializers import VendorSerializer, OrderHeaderSerializer, OrderLineItemSerializer, RawMaterialSerializer, RecipeHeaderSerializer, RecipeAmountSerializer
from rest_framework import viewsets, status, filters
from rest_framework.decorators import api_view


# Create your views here.
class VendorViewSet(viewsets.ModelViewSet):
    queryset = Vendor.objects.all()
    serializer_class = VendorSerializer


class OrderHeaderViewSet(viewsets.ModelViewSet):
    queryset = OrderHeader.objects.all()
    serializer_class = OrderHeaderSerializer


class OrderLineItemViewSet(viewsets.ModelViewSet):
    queryset = OrderLineItem.objects.all()
    serializer_class = OrderLineItemSerializer


class RawMaterialViewSet(viewsets.ModelViewSet):
    queryset = RawMaterial.objects.all()
    serializer_class = RawMaterialSerializer
    search_fields = ['id', 'raw_material_short_name']
    filter_backends = [filters.SearchFilter]


class RecipeHeaderViewSet(viewsets.ModelViewSet):
    queryset = RecipeHeader.objects.all()
    serializer_class = RecipeHeaderSerializer


class RecipeAmountViewSet(viewsets.ModelViewSet):
    queryset = RecipeAmount.objects.all()
    serializer_class = RecipeAmountSerializer