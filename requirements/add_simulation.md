# Cadastro

> ## Caso de sucesso

1. ✅ Recebe uma requisição do tipo **POST** na rota **/api/v1/simulation**
2. ✅ Valida dados obrigatórios:
   * origin
   * destination
   * planId (id valido de um plano adquirido por uma rota que disponibilizará esses dados)
   * timeInMinutes (Tempo de ligação em minutos) 
3. ✅ Valida que o planId deve existir no banco de dados
4. ✅ Valida se a simulação está calculando com a porcentagem correta
5. ✅ Retorna status 201 e um objeto com os dados da simulação retornando o valor calculado

> ## Exceções

1. ✅ Retorna erro **400** se os dados não forem informados
2. ✅  Retorna erro **406** se o plano não for encontrado no banco dados
3. ✅ Retorna erro **401** se não for informado um token válido
5. ✅ Retorna erro **500** se der erro interno no servidor