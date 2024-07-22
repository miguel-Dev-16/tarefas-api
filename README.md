# Curso Spring Treina (20/07/2024)

Vamos trabalhar com Spring Api Rest. utilizando as camadas (Model, Repository, Service e Controller)

No back-end  temos as seguintes comunicações, a camada de repository ela se comunica com a camada de service enviando entidades, que chamamos de classe modelo que fica na camada model, a camada de serviço ou service ela é responsável pela lógica de negócio, e por sua vez ela envia para o controller DTO. Na camada de controller ela é responsável por fazer a comunicação com o front-end através de requisições http, e estas requisições http ela tem métodos chamados de verbo http que são eles os principais GET, PUT, DELETE, POST. 