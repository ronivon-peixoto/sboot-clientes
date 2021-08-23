-- --------------------------------------------------------

--
-- Estrutura da tabela `cliente`
--

CREATE TABLE IF NOT EXISTS `cliente` (
	id bigint not null auto_increment, 
	documento varchar(14) not null, 
	nome varchar(255) not null, 
	email varchar(255), 
	endereco varchar(255), 
	tipo_documento varchar(4) not null, 
	primary key (id)
) engine=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


insert into cliente (documento, nome, email, endereco, tipo_documento) values 
	('42473649014', 'Juan Cau� Thiago das Neves', 'juancauethiagodasneves@ornatopresentes.com.br', 'Rua Anacleto Vitorino, 585 - Centro - Cabedelo-PB', 'CPF'),
	('04396563388', 'Ot�vio Osvaldo Viana', 'otavioosvaldoviana@prositeweb.com.br', 'Avenida Raulina Fonseca Pascoal, 550 - Setor Central - Catal�o-GO', 'CPF'),
	('50194852849', 'Cau� Lu�s Nathan da Rocha', 'ccaualuisnathandarocha@bakerhughes.com', 'Rua Vinte e Seis, 877 - Jardim Mariana - V�rzea Grande-MT', 'CPF'),
	('15175573721', 'Nathan Caleb Silveira', 'nathancalebsilveira@agsolve.com.br', 'Rua JC-04, 145 - Ol�mpico - Boa Vista-RR', 'CPF'),
	('33119294977', 'Rita Valentina Porto', 'ritavalentinaporto@belaggiovini.com.br', 'Rua Luzil�ndia, 756 - Parque Amaz�nia - Goi�nia-GO', 'CPF'),
	('62509968777', 'Manuela Agatha da Costa', 'manuelaagathadacosta@agsolve.com.br', 'Caminho 54-Quadra F, 366 - Cajazeiras - Salvador-BA', 'CPF'),
	('35281254545', 'Kaique Jorge Porto', 'kaiquejorgeporto@roche.com', 'Rua Adelina Lopes, 878 - Nova Bras�lia - Salvador-BA', 'CPF'),
	('83118882999', 'Henrique Rafael Baptista', 'hhenriquerafaelbaptista@robertacorrea.com', 'Rua Jerusal�m, 495 - Setor Parque Tremend�o - Goi�nia-GO', 'CPF'),
	('99036162521', 'Mirella Emily Gabriela Campos', 'mmirellaemilygabrielacampos@dlh.de', 'Travessa Quinze de Mar�o, 487 - Nova Rep�blica II - Santar�m-PA', 'CPF'),
	('94815123551', 'Bento Rodrigo Tom�s Jesus', 'bentorodrigotomasjesus@bigfoot.com', 'Rua Magalh�es Filho, 354 - Nossa Senhora das Gra�as - Teresina-PI', 'CPF'),
	('76178639260', 'Nicolas Leonardo Bento Sales', 'nicolasleonardobentosales@balaiofilmes.com.br', 'Rua Boa Ventura, 602 - Dom Pedro II - An�polis-GO', 'CPF'),
	('88333104509', 'T�nia Silvana Rocha', 'taniasilvanarocha@rotadasbandeiras.com.br', 'Rua S�o Manoel, 792 - Santiago - Ji-Paran�-RO', 'CPF'),
	('41109374500', 'Ian Eduardo Ribeiro', 'iianeduardoribeiro@ggm.com.br', 'Rua Vila Lobos, 312 - Tr�s Vendas - Pelotas-RS', 'CPF'),
	('01860328032', 'Raul Cau� Leonardo da Rosa', 'raulcaueleonardodarosa@zf-lenksysteme.com', 'Servid�o Mirante, 717 - Ribeir�o da Ilha - Florian�polis-SC', 'CPF'),
	('71867640457', 'Silvana Nina Vieira', 'silvananinavieira@tradevalle.com.br', 'Travessa S�o Jorge, 250 - S�o Bento - Boa Vista-RR', 'CPF'),
	('40748551786', 'Nat�lia Isis Nogueira', 'nataliaisisnogueira@img.com.br', 'Avenida Alvaro Carvalho Barbosa, 965 - Novo Horizonte - Macap�-AP', 'CPF'),
	('75962950291', 'Brenda Larissa Francisca da Cruz', 'brendalarissafranciscadacruz@tecvap.com.br', 'Rua Francisco de Castro, 182 - Fazendinha - Campo Largo-PR', 'CPF'),
	('23855751404', 'Ayla Regina Cristiane Peixoto', 'aylareginacristianepeixoto@monsanto.com', 'Rua Praia dos Ilh�us, 967 - Campestre - S�o Leopoldo-RS', 'CPF'),
	('14722993530', 'Yago Lorenzo Ferreira', 'yagolorenzoferreira@bidoul.eng.br', 'Rua B- 1, 252 - Morada do Ouro - Setor Norte - Cuiab�-MT', 'CPF'),
	('70018535348', 'Oliver Luan Severino Ferreira', 'oliverluanseverinoferreira@oticascarol.com.br', 'Rua Dom Pedro II, 188 - Loteamento Cellos - Rondon�polis-MT', 'CPF'),
	('51492658000107', 'Calebe e Guilherme Pizzaria Delivery ME', 'diretoria@calebepizzaria.com.br', 'Pra�a J�lio Rodrigues, 588 - Araras-SP', 'CNPJ'),
	('42725455000114', 'T�nia e Marcela Fotografias ME', 'producao@taniaemarcelafotografiasme.com.br', 'Rua Moacyr Matthiesen, 763 - Araras-SP', 'CNPJ'),
	('33657619000122', 'Andrea e Luiz Marcenaria ME', 'contato@andreaeluizmarcenariame.com.br', 'Alameda Francisco Torres, 440 - Jardim Jequitib�, Presidente Prudente-SP', 'CNPJ'),
	('41416814000199', 'Fabiana e Lorena Eletr�nica Ltda', 'tesouraria@fabianaelorenaeletronicaltda.com.br', 'Rua Ant�nio Barreto, 542 - Jardim Julieta, Po�-SP', 'CNPJ'),
	('13197486000141', 'Theo e Ruan Alimentos ME', 'faleconosco@theoalimentos.com.br', 'Rua Constantino Gaito, 192 - Jardim Marilu - S�o Paulo-SP', 'CNPJ');

