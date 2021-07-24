# Cadastro

> ## Caso de sucesso

1.  Recebe uma requisição do tipo **POST** na rota **/api/v1/signup**
2.  Valida dados obrigatórios **name**, **email**, **password** e **passwordConfirmation**
3.  Valida que **password** e **passwordConfirmation** são iguais
4.  Valida que o campo **email** é um e-mail válido
5.  **Valida** se já existe um usuário com o email fornecido
6.  Gera uma senha **criptografada** (essa senha não pode ser descriptografada)
7.  **Cria** uma conta para o usuário com os dados informados, **substituindo** a senha pela senha criptorafada
8.  Gera um **token** de acesso a partir do ID do usuário
9.  **Atualiza** os dados do usuário com o token de acesso gerado
10.  Retorna **200** com o token de acesso e o nome do usuário

> ## Exceções

1.  Retorna erro **400** se name, email, password ou passwordConfirmation não forem fornecidos pelo client
2.  Retorna erro **422** se password e passwordConfirmation não forem iguais
3.  Retorna erro **422** se o campo email for um e-mail inválido
4.  Retorna erro **409** se o email fornecido já estiver em uso
5.  Retorna erro **500** se der erro interno no servidor