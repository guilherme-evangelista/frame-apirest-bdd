#language: pt
#encoding: utf-8
#author: Guilherme Evangelista

@regression
Funcionalidade: Api de Movimentacoes

  @movimentacao
  Cenario: Devo inserir movimentacao em uma conta aleatorio com sucesso
    Dado que eu obtenha o token previamente do user "padrao"
    E certifique que eu tenho uma conta criada
    Quando faco uma requisicao Post na api de transacoes "com" token
    Então valido que a movimentacao foi inserida com sucesso