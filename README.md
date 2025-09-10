#  AppAgendaDeContatos

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)
[![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/)
[![Status](https://img.shields.io/badge/status-em%20desenvolvimento-yellow?style=for-the-badge)]

Aplicativo Android em **Java** para gerenciamento de contatos — uma ferramenta prática para cadastrar, editar, visualizar e excluir contatos de forma intuitiva.

---

##  Sobre o Projeto

O **AppAgendaDeContatos** é uma aplicação móvel Android escrita em Java, idealizada para ser uma **agenda digital simples e eficiente**. O foco é permitir que o usuário adicione, visualize, edite e remova contatos com facilidade, tudo em uma interface amigável e responsiva.

Este projeto é ideal para aprendizado de conceitos como:
- **CRUD** (Criação, Leitura, Atualização e Deleção)
- Utilização de **Activities**, **RecyclerView**, **Intents**
- Persistência de dados com **Room** ou **SQLite**
- Boas práticas de UI/UX no Android

---

##  Tecnologias Utilizadas

- **Java** — linguagem principal do app  
- **Android SDK** — plataforma de desenvolvimento móvel  
- **Android Studio** — ambiente de desenvolvimento integrado (IDE)  
- **Room** ou **SQLite** — persistência local dos dados (se implementado)  
- Componentes Android como: `RecyclerView`, `FloatingActionButton`, `AlertDialog`, `ViewModel` (dependendo da arquitetura)  

---

## 📂 Estrutura Sugerida

| Caminho                         | Descrição                                                       |
|---------------------------------|------------------------------------------------------------------|
| `app/`                          | Pasta principal do código-fonte do aplicativo                   |
| `app/src/main/java/...`         | Pacotes de atividades e modelos (Activities, Adapters, Models, DB) |
| `app/src/main/res/...`          | Recursos visuais: layouts XML, strings, cores, estilos          |
| `build.gradle`                  | Configuração do build do módulo App                             |
| `settings.gradle`               | Configuração geral do projeto                                   |
| `README.md`                     | Documentação do projeto                                         |

---

##  Funcionalidades (Atual e Previstas)

-  Tela de listagem de contatos (com nome e telefone)
-  Adição de novo contato (nome, telefone, e-mail opcional)
-  Edição e exclusão de contatos existentes
-  Pesquisa ou filtro simples na lista (se implementado)
- 🔜 Armazenamento local (Room ou SQLite)
- 🔜 Interface com **Material Design**
- 🔜 Interface moderna com **tema claro/escuro**
- 🔜 Possível exportação/importação de contatos em CSV

---

##  Como Executar

1. Clone este repositório:
   ```bash
   git clone https://github.com/arthurx034/AppAgendaDeContatos.git
