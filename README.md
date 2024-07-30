# Curso Spring Treina (20/07/2024)

Vamos trabalhar com Spring Api Rest. utilizando as camadas (Model, Repository, Service e Controller)

No back-end  temos as seguintes comunicações, a camada de repository ela se comunica com a camada de service enviando entidades, que chamamos de classe modelo que fica na camada model, a camada de serviço ou service ela é responsável pela lógica de negócio, e por sua vez ela envia para o controller DTO. Na camada de controller ela é responsável por fazer a comunicação com o front-end através de requisições http, e estas requisições http ela tem métodos chamados de verbo http que são eles os principais GET, PUT, DELETE, POST.  Então a camada de Controller ela se comunica com o service que por sua vez o service envia DTO para a mesma. E o controller se comunica com o front através dos verbos http enviando e recebendo arquivos JSON.

![](imgs\diagrama-rest.png)

[TOC]





## Model

Nesta camada ela vai ser a classe de modelo, onde vai ter os métodos e atributos, nela vamos usar o Lombok para gerar nossos métodos (getts, setts, construtores) . Abaixo segue o código comentado!

```java
@Entity //-> indica que é uma entidade para criação de uma tabela no banco de dados.
@Table(name = "tar_usuarios")
@Data //ele coloca o gett,sett,equals e hashCode
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private  Long id;
   
   @Column(nullable = false)
   private  String nome;
   
   @Column(nullable = false, unique = true)
   private  String email;
   
   @Column(nullable = false)
   private  String senha;
   
   @JsonFormat(pattern = "dd/MM/yyyy")//tem que vim neste formato Json
   @Column(nullable = false) //no banco de dados não aceita valor nulo,se não colocar a anotação ele vai aceitar.
   private  LocalDate dataNascimento;
   
   /* 1) metodo para converter usuario para dto
   public UsuarioDto2 convertParaDto() {
	   UsuarioDto2 dto = new UsuarioDto2();
	   dto.setId(id);
	   dto.setNome(nome);
	   dto.setEmail(email);
	   dto.setIdade(Period.between(dataNascimento, LocalDate.now()).getYears());
	
	   return dto;
   }*/
    
    
   //segundo método mais elegante
     public UsuarioDto2 convertParaDto() {
	   return new UsuarioDto2(id, nome, email, Period.between(dataNascimento, LocalDate.now()).getYears());
   }
    
}

```

Nesta camada também estamos usando o JPA, onde estas anotações vai gerenciar os dados no nosso banco de dados. Segue abaixo algumas anotações usadas no JPA.

```jpa
@Entity -> indica que é uma entidade para criação de uma tabela no banco de dados.
@Table(name = "tar_usuarios") -> Defino como vai ser o nome fa minha tabela no banco.
@Id -> indica a chave primária

```

## Dto

Ele é usado quando eu não quero mandar todos os atributos da minha classe para tela e sim o que eu vou precisar. então no caso eu crio um dto. Nesta classe pode ter anotação do lombok mais não tem mais nenhuma pois é uma classe simples. Exemplo:

1) forma de DTO:

```java
@Getter
public class UsuarioDto {
	private  Long id;
	private  String nome;
	private  String email;
	
	public UsuarioDto(Usuario entity) {
		id   = entity.getId();
		nome = entity.getNome();
		email = entity.getEmail();
	}
}
//No meu Service eu posso fazer da seguinte forma para retornar uma lista de DTO ou um usuario de DTO

@Service
public class UsuarioService {
    
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	public Usuario cadastrar(Usuario objs) {
	  return usuarioRepository.save(objs);//ele retorna o objeto cadastrado.
	}
	
	public List<UsuarioDto> listarUsuarios(){
		List<Usuario> us =  usuarioRepository.findAll();
		return us.stream().map(x -> new UsuarioDto(x)).toList();
	}
	
    /*Aqui eu crio um método para me retornar um Usuario DTO e faço a conversão dele aqui */
	public UsuarioDto pegaUsuario(Long id) {
		Usuario u = usuarioRepository.findById(id).get();
		return new UsuarioDto(u);
	}
	
}

```

2) forma DTO

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto2 {
	
	private Long id;
	private String nome;
	private String email;
	private Integer idade;
}

// Crio um método para converter para DTO no meu model Usuário
@Entity
@Table(name = "tar_usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private  Long id;
   
   @Column(nullable = false)
   private  String nome;
   
   @Column(nullable = false, unique = true)
   private  String email;
   
   @Column(nullable = false)
   private  String senha;
   
   @JsonFormat(pattern = "dd/MM/yyyy")
   @Column(nullable = false)
   private  LocalDate dataNascimento;
   
   //metodo para converter usuario para dto
   public UsuarioDto2 convertParaDto() {
	   UsuarioDto2 dto = new UsuarioDto2();
	   dto.setId(id);
	   dto.setNome(nome);
	   dto.setEmail(email);
	   dto.setIdade(Period.between(dataNascimento, LocalDate.now()).getYears());
	   return dto;
   } 
   
}

// e no service eu converto chamando o metodo do meu model.
@Service
public class UsuarioService {
    
public UsuarioDTO buscarUsuario(Long id) {
        /*como o findById() ele me retorna um otional e o optional ele ja trata a ecxeção para mim pode-se fazer deste 
        jeito também.*/
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

        if (usuarioOpt.isPresent()) {//isPresent -> se ele existir execute o codigo abaixo.
            return usuarioOpt.get().converterParaDTO();
        }
        return null;
    }
    
}



```



### Lombok

Ele serve para gerar nossos métodos Getters, Setters,  Equals e HashCode e construtores. Abaixo segue as principais anotações do Lombok.

```lombok
@NonNull -> para informar que o atributo é não nullo
@Getter/@Setter -> criando os métodos getters e setters.
@ToString -> Criando o método toString() do java
@EqualsAndHashCode -> cria os dois métodos equals e hashCode
@NoArgsConstructor -> construtor vazio. 
@AllArgsConstructor -> construtor com todos os atributos da classe.
@Data -> ele gera o @ToString, @Getter/@Setter e @EqualsAndHashCode.
```



## Repository

Ele é responsável por nossa camada de dados, ele é uma interface que herda uma interface chamada JpaRepository onde podemos usar métodos já prontos da nossa camada. Para isso precisamos criar a nossa  interface e implementar o JpaRepository < T,  ID>, como ele é um tipo genérico precisamos passar a classe e o tipo da sua primary key  da classe. 

Obs: Está camada usa o model.

Segue o exemplo abaixo.

```java
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{	
}
```

Também podemos criar nossas consultas customizadas sem precisar colocar comandos Sql nativo.  Para isso vamos usar para a assinatura do nosso método a palavra findBy e depois colocamos o nome do nosso atributo podendo usar o camelCase. E depois do atributo podemos colocar alguns comandos sql como findByNomeLike Exemplo abaixo.

```java
//nesta camada não precisa colocar o @Repository pois como herda de JpaRepository o Spring entende que é um Repository.

                                                       //classe ,Tipo primary key
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
	//aqui ele busca por palavras exemplo as duas primeiras letras "mi".
    /*aqui ele consegue fazer uma busca por nome, e usando a palavra reservada findBy e depois colocando o atributo ficando       	findByNome o spring ja sabe que precsisa filtrar pelo nome, e se quiser depois do atributo pode colocar um sql como no 			exemplo  o like*/
	List<Usuario> findByNomeLike(String nome);
	
	//buscar por email do usuáro
	Optional<Usuario> findByEmail(String email);
	
	//buscar em ma faxa de datas
	List<Usuario> findByDataNascimentoBetween(LocalDate inicio, LocalDate fim);
}

```



## Service

Nesta camada vai ficar a lógica de negocio e ela chama o repository e envia para o controller DTO. Nesta classe service eu preciso informar ao Spring que é uma classe de serviço e para isso eu coloco a anotação @Service. Segue o exemplo abaixo:

```java
@Service
public class UsuarioService {
    //aqui eu meio que instancio meu Usuario repository para poder chamar os métodos desta camada.
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	public Usuario cadastrar(Usuario objs) {
	  return usuarioRepository.save(objs);//ele salva o objeto no banco de dados e retorna o objeto que foi cadastrado.
	}
	
	public List<UsuarioDto> listarUsuarios(){
		List<Usuario> us =  usuarioRepository.findAll();//este método findll() ele me retorna uma lista de objetos
		return us.stream().map(x -> new UsuarioDto(x)).toList();// eu uso este método para converter uma lista de objetos para dto
	}
	
	/*a diferença do optional para usar o get() é que ele já trata a exceção*/
	public Optional<Usuario> buscarUsuario(Long id){
		return usuarioRepository.findById(id);
	}
    
    //exemplo sem o optional quando eu retorno um objeto invez do Optional se de algum erro eu preciso tratar e o método do meu     repository ele me retorna um Optional ai para ele me retornar um objeto eu preciso colocar o .get() ficando     	 			usuarioRepository.findById(id).get();
    public Usuario buscarUsuario(Long id){
		return usuarioRepository.findById(id).get();
	}
    
	
	public void removerPorId(Long id) {
		usuarioRepository.deleteById(id);
	}
	
	public Optional<Usuario> getUserEmail(String email){
		return usuarioRepository.findByEmail(email);
	}
	
	public List<Usuario> filtrarPorData(LocalDate inicio, LocalDate fim){
		return usuarioRepository.findByDataNascimentoBetween(inicio, fim);
	}
    
    public List<Usuario> buscarUsuariosPeloNome(String nome) {
        return usuarioRepository.findByNomeLike("%" + nome + "%");//no like eu tenho que usar % atributo %
    }
    
    //aqui ele busca pelo id do usuario, mais aqui eu chamo um método criado na minha entidade para converter Usuario em          	  UsuarioDto. 
    public UsuarioDTO buscarUsuario(Long id) {
    Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

        if (usuarioOpt.isPresent()) {//isPresent -> se ele existir execute o codigo abaixo.
            return usuarioOpt.get().converterParaDTO();
    }
      	 return null;
    }
	
}
```



## Controller

É responsável por receber receber o service onde nele tem os DTOs e regras de negocio, e fazer a comunicação  com o front end através dos verbos http onde ele serializa e de serializa. Exemplo abaixo:

```java
@RestController //preciso desta anotação para informar para o spring que é uma class de controller
@RequestMapping("/usuarios") //aqui faço o mapeamento da url, tambem chamado de and point.
public class UsuarioController {
    
    //aqui chamo o service 
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.salvarUsuario(usuario));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok().body(usuarioService.listarUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarUsuarioPeloId(@PathVariable("id") Long id) {
        UsuarioDTO usuario = usuarioService.buscarUsuario(id);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok().body(usuario);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> buscarUsuarioPeloEmail(@PathVariable("email") String email) {
        Optional<Usuario> usuario = usuarioService.buscarUsuarioPeloEmail(email);

        if (usuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok().body(usuario.get());
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Usuario>> buscarUsuariosPeloNome(@PathVariable("nome") String nome) {
        return ResponseEntity.ok().body(usuarioService.buscarUsuariosPeloNome(nome));
    }

    @GetMapping("/data")
    public ResponseEntity<List<Usuario>> filtrarUsuariosPelaDataNascimento(
        @RequestParam("dataInicio") LocalDate dataInicio,
        @RequestParam("dataFim") LocalDate dataFim) {
        return ResponseEntity.ok().body(usuarioService.buscarUsuariosPelaDataNascimento(dataInicio, dataFim));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuarioPeloId(@PathVariable("id") Long id) {
        UsuarioDTO usuario = usuarioService.buscarUsuario(id);

        if (Objects.isNull(usuario)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        usuarioService.deletarUsuario(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(
        @PathVariable("id") Long id, 
        @RequestBody Usuario usuarioAtualizado) {
            UsuarioDTO usuario = usuarioService.buscarUsuario(id);

            if (Objects.isNull(usuario)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            usuarioAtualizado.setId(id);
            usuarioAtualizado = usuarioService.salvarUsuario(usuarioAtualizado);

            return ResponseEntity.ok().body(usuarioAtualizado.converterParaDTO());
    }
    
}

```

### Dicionário 

```jpa
@RestController -> preciso desta anotação para informar para o spring que é uma class de controller
@RequestMapping("/usuarios") -> aqui faço o mapeamento da url, também chamado de and point.
@PostMapping -> quando os dados vem via metodo post ele vem no corpo da requisição 
@GetMapping() -> quando os dados vem pela a url geralmente consultas.
@GetMapping("/data") -> eu crio um outro mapeamento
@DeleteMapping("/{id}") -> um mapeamento para deletar.


1) Usando o @RequestBody para pegar valores no corpo da requisição.
    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.salvarUsuario(usuario));
    }

2) Pegando valores por parametros que vem na url:
   @DeleteMapping("/{id}") -> aqui coloco o parametro id que vem da url, e no @PathVariable eu coloco o tipo e o atributo tem que ser o mesmo do parametro.
    public ResponseEntity<Void> deletarUsuarioPeloId(@PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.buscarUsuario(id);

        if (Objects.isNull(usuario)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        usuarioService.deletarUsuario(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



OBS: eu posso receber dois parametros no meu método como (@PathVariable Long id,@RequestBody Usuario u),
tambem posso (@PathVariable Long id, @PathVariable Long codigo)

mais exemplos:

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable("id") Long id, 
            @RequestBody Usuario usuarioAtualizado) {
            
            UsuarioDTO usuario = usuarioService.buscarUsuario(id);

            if (Objects.isNull(usuario)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            usuarioAtualizado.setId(id);//acredito que esta parte ficou redundante
            usuarioAtualizado = usuarioService.salvarUsuario(usuarioAtualizado);

            return ResponseEntity.ok().body(usuarioAtualizado.converterParaDTO());
    }


Diferença do @RequestParam para o @PathVariable:
1) @RequestParam você passa os parâmetros na url o atributo e o valo como no  exemplo abaixo:
 
  http://localhost:8080/usuario?email=lorem@hotmail.com 
 
  @GetMapping("/usuario")
  public Usuario buscarporEmail(@RequestParam String email){
 
 } 
obs: passamos valor na url direto colocando ?atributo=valor se caso for passar mais de um valor ?atributo=valor&atributo=valor

2)@PathVariable aqui você só passa o valor direto sem o atributo exemplo abaixo:
  
  http://localhost:8080/usuario/nome/miguel
  
 
  @GetMapping("/usuario/nome/{nome}")
  public String buscarNome(@PathVariable String nome){
   return nome;
  }

  
```



## Documentação Swegger

para ver a documentação no swegger colocamos está url: http://localhost:8080/swagger-ui/index.html#/

colocamos nas dependências  do maven a dependência do swegger .



## ResponseEntity para Retornar o Status:

Usamos para retornar um status para o url, como status 200 ok. Segue o exemplo abaixo.

```java
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @GetMapping
    public ResponseEntity ****() {
        //quando da um retorno de uma lista
        return ResponseEntity.ok().body(usuarioService.listarUsuarios());
        //quando salvo um usuário
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.salvarUsuario(usuario));
        //retorna um erro de um objeto nulo
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        //quando o usuário é deletado
         return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        
    }
    
    explicação de cada no de método:
    return ResponseEntity.ok() //o retorno do status
        .body(usuarioService.listarUsuarios());// no body é o que vai ser devolvido para a requisição.
    /////////////////////////////////////////////////////////////////////////////////////////////////
    return ResponseEntity.status(HttpStatus.CREATED)//retornando o status para a requisição
        .body(usuarioService.salvarUsuario(usuario))//no body é o que vai ser devolvido para a requisição.
    
    
}
```



## Resumo

Podemos enviar dados pela a url pelo método Get ou por parâmetros na URL, e podemos enviar dados através do corpo da requisição por um formulário pelo método Post.

Os dados enviados podemos pegar através do @RequestParam, @PathVariable ou @RequestBody e outros.



​                                                 Diagrama do funcionamento de uma API Rest.

![](imgs\diagrama-rest-2.png)

​                                                Diagrama da resposta de uma Requisição.

![](imgs\diagrama-rest-3.png)
