#!/bin/bash
# =============================================================================
# AirWatch - Script de criação da VM na Azure via CLI
# Grupo Solsticio | FIAP 2026
# Execute: bash azure-cli-script.sh
# =============================================================================

set -e

# ── Variáveis ─────────────────────────────────────────────────────────────────
RESOURCE_GROUP="airwatch-rg"
VM_NAME="airwatch-vm"
LOCATION="eastus"
VM_SIZE="Standard_B2s"
ADMIN_USER="azureuser"
ADMIN_PASSWORD="AirWatch2026!"
IMAGE="Ubuntu2204"

echo "============================================"
echo " AirWatch - Criando infraestrutura na Azure"
echo "============================================"

# 1. Login (pula se já estiver logado)
echo "[1/6] Fazendo login na Azure..."
az login --use-device-code

# 2. Criar Resource Group
echo "[2/6] Criando Resource Group: $RESOURCE_GROUP"
az group create \
  --name $RESOURCE_GROUP \
  --location $LOCATION

# 3. Criar a VM
echo "[3/6] Criando VM: $VM_NAME"
az vm create \
  --resource-group $RESOURCE_GROUP \
  --name $VM_NAME \
  --image $IMAGE \
  --size $VM_SIZE \
  --admin-username $ADMIN_USER \
  --admin-password $ADMIN_PASSWORD \
  --authentication-type password \
  --public-ip-sku Standard \
  --output json

# 4. Abrir porta 8080 (API)
echo "[4/6] Abrindo porta 8080 (API Spring Boot)"
az vm open-port \
  --resource-group $RESOURCE_GROUP \
  --name $VM_NAME \
  --port 8080 \
  --priority 1001

# 5. Abrir porta 1521 (Oracle)
echo "[5/6] Abrindo porta 1521 (Oracle)"
az vm open-port \
  --resource-group $RESOURCE_GROUP \
  --name $VM_NAME \
  --port 1521 \
  --priority 1002

# 6. Pegar IP público da VM
echo "[6/6] Obtendo IP público da VM..."
IP=$(az vm list-ip-addresses \
  --resource-group $RESOURCE_GROUP \
  --name $VM_NAME \
  --query "[0].virtualMachine.network.publicIpAddresses[0].ipAddress" \
  --output tsv)

echo ""
echo "============================================"
echo " VM criada com sucesso!"
echo " IP Público: $IP"
echo " Usuário: $ADMIN_USER"
echo " Senha: $ADMIN_PASSWORD"
echo ""
echo " Conecte via SSH:"
echo " ssh $ADMIN_USER@$IP"
echo ""
echo " Swagger (após deploy):"
echo " http://$IP:8080/swagger-ui.html"
echo "============================================"
