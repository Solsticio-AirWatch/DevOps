# AirWatch 🌍
### Plataforma de Monitoramento de Qualidade do Ar
**Grupo Solsticio | FIAP 2026 | DevOps Tools & Cloud Computing**

---

## 📋 Sobre o Projeto

O **AirWatch** é uma plataforma que monitora a qualidade do ar em tempo real usando dados de satélites (NASA, ESA, OpenAQ) e sensores IoT (ESP32). A solução conecta tecnologia espacial a um problema urbano real: a poluição atmosférica que afeta milhões de pessoas.

### Tecnologias
- **Backend:** Java 17 + Spring Boot 3.2
- **Banco de Dados:** Oracle XE 21c
- **Containerização:** Docker + Docker Compose
- **Cloud:** Microsoft Azure (VM Ubuntu 22.04)
- **Autenticação:** JWT

---

## 🏗️ Arquitetura Macro

```
┌─────────────────────────────────────────────────────────┐
│                   AZURE VM (Ubuntu 22.04)                │
│                   Standard_B2s (2 vCPU, 4GB RAM)        │
│                                                          │
│   ┌──────────────────────┐   ┌─────────────────────┐    │
│   │  airwatch-RM565760   │   │  oracle-RM565760     │    │
│   │  (Spring Boot API)   │──▶│  (Oracle XE 21c)     │    │
│   │  Port: 8080          │   │  Port: 1521          │    │
│   │  User: airwatch      │   │  Volume: nomeado     │    │
│   └──────────────────────┘   └─────────────────────┘    │
│              │                         │                 │
│              └─────────────────────────┘                 │
│                    airwatch-network                      │
└─────────────────────────────────────────────────────────┘
         │
         │ HTTP :8080
         ▼
   [ Internet / Browser ]
   http://IP_VM:8080/swagger-ui.html
```

> **Diagrama completo:** [Draw.io - Arquitetura AirWatch](https://app.diagrams.net/)

---

## 🚀 How To — Do Clone ao Deploy em Nuvem

### Pré-requisitos
- Docker Desktop instalado
- Git instalado
- Conta Azure ativa
- Azure CLI instalado

---

### ETAPA 1 — Clonar o repositório

```bash
git clone https://github.com/SEU_USUARIO/airwatch.git
cd airwatch
```

---

### ETAPA 2 — Criar a VM na Azure

**Opção A — Via Portal Azure (interface gráfica):**
```
1. Acesse: https://portal.azure.com
2. Máquinas Virtuais → Criar → Máquina Virtual do Azure
3. Configure:
   - Grupo de recursos: airwatch-rg (criar novo)
   - Nome: airwatch-vm
   - Região: East US
   - Imagem: Ubuntu Server 22.04 LTS
   - Tamanho: Standard_B2s
   - Usuário: azureuser
   - Senha: AirWatch2026!
4. Em "Rede": deixar criar automaticamente
5. Revisar + Criar → Criar
6. Após criar: vá em Rede → Adicionar regra de entrada
   - Porta 8080 (API) — prioridade 1001
   - Porta 1521 (Oracle) — prioridade 1002
7. Anote o IP público da VM
```

**Opção B — Via Azure CLI:**
```bash
# Instalar Azure CLI: https://docs.microsoft.com/cli/azure/install-azure-cli
bash azure-cli-script.sh
```

---

### ETAPA 3 — Conectar na VM via SSH

```bash
# Substituir pelo IP da sua VM
ssh azureuser@IP_DA_VM
# Senha: AirWatch2026!
# Digite "yes" na primeira conexão
```

---

### ETAPA 4 — Instalar Docker na VM

```bash
# Atualizar sistema
sudo apt-get update -y

# Instalar dependências
sudo apt-get install -y ca-certificates curl gnupg lsb-release git

# Adicionar chave GPG Docker
sudo install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | \
  sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
sudo chmod a+r /etc/apt/keyrings/docker.gpg

# Adicionar repositório Docker
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] \
  https://download.docker.com/linux/ubuntu \
  $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

# Instalar Docker Engine + Compose
sudo apt-get update -y
sudo apt-get install -y docker-ce docker-ce-cli containerd.io \
  docker-buildx-plugin docker-compose-plugin

# Adicionar usuário ao grupo docker
sudo usermod -aG docker azureuser
newgrp docker

# Verificar
docker --version
docker compose version
```

---

### ETAPA 5 — Clonar o projeto na VM e subir os containers

```bash
# Clonar na VM
git clone https://github.com/SEU_USUARIO/airwatch.git
cd airwatch

# ✅ Subir ambos containers em BACKGROUND (modo segundo plano)
docker compose up --build -d

# Verificar status dos containers
docker ps
```

---

### ETAPA 6 — Monitorar os logs

```bash
# ✅ Exibir logs de AMBOS os containers no terminal
docker compose logs -f

# Só o Oracle (aguarde "DATABASE IS READY TO USE!")
docker compose logs -f oracle-RM565760

# Só a API
docker compose logs -f airwatch-RM565760
```

---

### ETAPA 7 — Executar os scripts SQL no banco

```bash
# Copiar scripts para o container Oracle
docker cp airwatch_ddl.sql   oracle-RM565760:/tmp/ddl.sql
docker cp airwatch_dml.sql   oracle-RM565760:/tmp/dml.sql

# Executar DDL (cria tabelas)
docker container exec oracle-RM565760 \
  sqlplus airwatch/airwatch123@//localhost:1521/XEPDB1 @/tmp/ddl.sql

# Executar DML (insere dados de teste)
docker container exec oracle-RM565760 \
  sqlplus airwatch/airwatch123@//localhost:1521/XEPDB1 @/tmp/dml.sql
```

---

### ETAPA 8 — Evidências obrigatórias nos containers

```bash
# ✅ Acessar container da APLICAÇÃO e demonstrar:
docker container exec -it airwatch-RM565760 sh

# Dentro do container:
whoami          # deve mostrar: airwatch
pwd             # deve mostrar: /app/airwatch
ls -l           # lista arquivos do diretório de trabalho
exit

# ✅ Acessar container do BANCO e demonstrar:
docker container exec -it oracle-RM565760 bash

# Dentro do container:
whoami          # deve mostrar: oracle
pwd             # deve mostrar diretório atual
ls -l           # lista arquivos
exit
```

---

### ETAPA 9 — Evidências de persistência no banco (SELECT)

```bash
# ✅ Conectar DIRETAMENTE no container do banco e fazer SELECT
docker container exec -it oracle-RM565760 \
  sqlplus airwatch/airwatch123@//localhost:1521/XEPDB1

-- Dentro do SQL*Plus:
-- Evidência tabela COUNTRY
SELECT * FROM COUNTRY;

-- Evidência tabela CITY
SELECT * FROM CITY;

-- Evidência tabela USERS
SELECT id_user, name, email, role FROM USERS;

-- Evidência tabela AIR_READING
SELECT id_reading, id_city, pm25, aqi, category FROM AIR_READING;

-- Evidência de INSERT (Create)
INSERT INTO COUNTRY (id_country, name, iso_code, continent)
VALUES (SEQ_COUNTRY.NEXTVAL, 'Portugal', 'PT', 'Europe');
COMMIT;
SELECT * FROM COUNTRY WHERE iso_code = 'PT';

-- Evidência de UPDATE
UPDATE COUNTRY SET name = 'Portugal Updated' WHERE iso_code = 'PT';
COMMIT;
SELECT * FROM COUNTRY WHERE iso_code = 'PT';

-- Evidência de DELETE
DELETE FROM COUNTRY WHERE iso_code = 'PT';
COMMIT;
SELECT COUNT(*) FROM COUNTRY;

EXIT;
```

---

### ETAPA 10 — Testar a API em nuvem

```bash
# Health check
curl http://IP_DA_VM:8080/actuator/health
# Esperado: {"status":"UP"}

# Registrar usuário
curl -X POST http://IP_DA_VM:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Admin","email":"admin@airwatch.com","password":"admin123","role":"ADMIN"}'

# Login e obter token
curl -X POST http://IP_DA_VM:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@airwatch.com","password":"admin123"}'

# Listar países (público)
curl http://IP_DA_VM:8080/api/countries

# Listar cidades (público)
curl http://IP_DA_VM:8080/api/cities
```

**Swagger UI (pelo navegador):**
```
http://IP_DA_VM:8080/swagger-ui.html
```

---

### ETAPA 11 — Atualizar após mudanças no código

```bash
# No seu computador:
git add .
git commit -m "feat: descrição da mudança"
git push origin main

# Na VM:
cd ~/airwatch
git pull origin main
docker compose up --build -d airwatch-RM565760
docker compose logs -f airwatch-RM565760
```

---

## 📦 Estrutura do Projeto

```
airwatch/
├── src/                          # Código fonte Java
│   └── main/
│       ├── java/br/com/fiap/airwatch/
│       │   ├── country/          # CRUD Country
│       │   ├── city/             # CRUD City
│       │   ├── users/            # CRUD Users + Auth
│       │   ├── sensor/           # CRUD Sensor
│       │   ├── airreading/       # CRUD Air Reading
│       │   ├── alertconfig/      # CRUD Alert Config
│       │   ├── alertevent/       # CRUD Alert Event
│       │   ├── integrationlog/   # CRUD Integration Log
│       │   ├── config/           # Security, Swagger, JWT
│       │   └── exception/        # Exception handlers
│       └── resources/
│           ├── application.properties
│           ├── application-fiap.properties
│           ├── application-local.properties
│           └── application-prod.properties
├── Dockerfile                    # Multi-stage build
├── docker-compose.yml            # API + Oracle
├── azure-cli-script.sh           # Criação da VM via CLI
├── pom.xml                       # Dependências Maven
└── README.md                     # Este arquivo
```

---

## 🗄️ Banco de Dados — Tabelas Principais

| Tabela | Descrição |
|---|---|
| `COUNTRY` | Países monitorados |
| `CITY` | Cidades com geolocalização |
| `USERS` | Usuários do sistema |
| `SENSOR` | Sensores IoT cadastrados |
| `AIR_READING` | Leituras de qualidade do ar |
| `ALERT_CONFIG` | Configurações de alertas |
| `ALERT_EVENT` | Histórico de alertas disparados |
| `INTEGRATION_LOG` | Log de chamadas às APIs externas |

---

## 🔗 Endpoints Principais

| Método | Endpoint | Descrição |
|---|---|---|
| POST | /api/auth/register | Criar usuário |
| POST | /api/auth/login | Autenticar (retorna JWT) |
| GET | /api/countries | Listar países |
| GET | /api/cities | Listar cidades |
| GET | /api/sensors | Listar sensores |
| GET | /api/air-readings | Listar leituras |
| POST | /api/air-readings | Registrar leitura |
| GET | /api/alert-configs | Listar alertas |
| GET | /api/alert-events | Histórico de eventos |

> Documentação completa: `http://IP_DA_VM:8080/swagger-ui.html`

---

## 🎥 Roteiro do Vídeo Demonstrativo

1. Mostrar o repositório no GitHub
2. Clonar o repositório na VM via SSH
3. Executar `docker compose up --build -d`
4. Exibir logs: `docker compose logs -f`
5. Verificar `docker ps` — mostrar containers rodando
6. Acessar container da API: `docker container exec -it airwatch-RM565760 sh`
   - Rodar: `whoami`, `pwd`, `ls -l`
7. Acessar container do Oracle: `docker container exec -it oracle-RM565760 bash`
   - Rodar: `whoami`, `pwd`, `ls -l`
8. Conectar no banco via sqlplus e rodar SELECT em 2 tabelas
9. Demonstrar CRUD completo no Swagger: `http://IP_DA_VM:8080/swagger-ui.html`
10. Mostrar persistência: criar registro no Swagger → SELECT no banco

---

## 👥 Grupo Solsticio

| RM | Nome | Turma |
|---|---|---|
| RM565760 | Enrico Delesporte | 2TDS |
| RM561810 | Felipe Modesto | 2TDS |
| RM565422 | Vitor Dias | 2TDS |

---

*AirWatch © Grupo Solsticio - FIAP 2026*
