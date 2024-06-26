from django.shortcuts import render
from backend_api.models import Vendor, OrderHeader, OrderLineItem, RawMaterial, RecipeHeader, RecipeCost
from backend_api.serializers import VendorSerializer, OrderHeaderSerializer, OrderLineItemSerializer, RawMaterialSerializer, RecipeHeaderSerializer, RecipeCostSerializer
from rest_framework import viewsets, status
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


class RecipeHeaderViewSet(viewsets.ModelViewSet):
    queryset = RecipeHeader.objects.all()
    serializer_class = RecipeHeaderSerializer


class RecipeCostViewSet(viewsets.ModelViewSet):
    queryset = RecipeCost.objects.all()
    serializer_class = RecipeCostSerializer