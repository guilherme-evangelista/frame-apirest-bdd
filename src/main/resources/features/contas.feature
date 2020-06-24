#language: pt
#encoding: utf-8
#author: Guilherme Evangelista

  @regression @contas
  Funcionalidade: Api de Contas

    @resquisicaoSemToken @negativo
    Cenario: Nao conseguir resiquitar minha api sem token
      Quando faco uma requisicao GET na api de contas "sem" token
      Entao valido o status code 401
      E valido a mensagem de usuario nao autorizado

    @incluirConta @positivo
    Cenario: Incluir conta com sucesso
      Dado que eu obtenha o token previamente do user "padrao"
      Quando faco uma requisicao POST na api de contas "com" token
      Entao valido o status code 201
      E valido que o nome da conta foi inserido de acordo

    @alterarConta @positivo
    Cenario: Alterar conta com sucesso
      Dado que eu obtenha o token previamente do user "padrao"
      Quando faco uma requisicao PUT na api de contas "com" token
      Entao valido o status code 200
      E valido que o nome da conta foi alterado de acordo

    @incluirContaRepetida @negativo
    Cenario: Não conseguir incluir conta com nome repetido
      Dado que eu obtenha o token previamente do user "padrao"
      Quando faco uma requisicao POST na api de contas "com" token e com nome repetido
      Entao valido o status code 400
      E valido a mensagem "Já existe uma conta com esse nome!"