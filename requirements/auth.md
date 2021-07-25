# Cadastro

> ## Caso de sucesso

1.  Recebe uma requisição do tipo **POST** na rota **/oauth/token**
2.  Valida dados obrigatórios **email** e **password**
3.  Retorna status **200** e um token com data de expira

> ## Exceções

1.  Retorna erro **400** se email e password não forem informados
2.  Retorna erro **401** se as credencias forem invalidas
3.  Retorna erro **500** se der erro interno no servidor