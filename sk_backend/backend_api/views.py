from django.shortcuts import render
from backend_api.models import Vendor, OrderHeader, OrderLineItem, RawMaterial, RecipeHeader, RecipeAmount, RecipeItem
from backend_api.serializers import VendorSerializer, OrderHeaderSerializer, \
    OrderLineItemSerializer, RawMaterialSerializer, RecipeHeaderSerializer, \
    RecipeAmountSerializer, RawMaterialNameSubSerializer
from rest_framework import viewsets, status, filters, generics
from rest_framework.decorators import api_view
from django.contrib.auth.decorators import login_required
from rest_framework.response import Response

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

#
# class RecipeAmountListView(generics.ListCreateAPIView):
#     queryset = RecipeAmount.objects.all()
#     serializer_class = RecipeAmountSerializer
#
# class RecipeAmountDetailView(generics.RetrieveUpdateDestroyAPIView):
#     queryset = RecipeAmount.objects.all()
#     serializer_class = RecipeAmountSerializer


class RecipeAmountViewSet(viewsets.ModelViewSet):
    queryset = RecipeAmount.objects.all()
    serializer_class = RecipeAmountSerializer


# @login_required
@api_view(['GET'])
def myview(request):
    # thing_in_question = RecipeItem.objects.all()
    thing_in_question = RecipeAmount.objects.select_related()
    serializer = RecipeAmountSerializer(thing_in_question, many=True)
    return Response(serializer.data)
