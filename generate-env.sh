#!/bin/bash

echo "Gerando arquivo .env a partir das variáveis de ambiente..."

echo "DEBUG: Variáveis carregadas:"
echo "SUPABASE_URL=$SUPABASE_URL"


cat <<EOF > .env
SUPABASE_URL=${SUPABASE_URL}
SUPABASE_KEY=${SUPABASE_KEY}
SUPABASE_DB=${SUPABASE_DB}
SUPABASE_USER=${SUPABASE_USER}
SUPABASE_PASSWORD=${SUPABASE_PASSWORD}
EOF
