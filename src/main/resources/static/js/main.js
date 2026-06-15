// LOGIN

// PEGA O FORMULÁRIO DE LOGIN PELO ID
const formLogin = document.getElementById("form-login");

// VERIFICA SE O FORMULÁRIO DE LOGIN EXISTE NA PÁGINA
if(formLogin){

    // ADICIONA UM EVENTO QUANDO O FORMULÁRIO FOR ENVIADO
    formLogin.addEventListener("submit", function(event){

        // IMPEDE O ENVIO PADRÃO DO FORMULÁRIO
        event.preventDefault();

        // REDIRECIONA PARA A PÁGINA DE EVENTOS
        window.location.href = "eventos.html";

    });

}


// CADASTRO

// PEGA O FORMULÁRIO DE CADASTRO PELO ID
const formCadastro = document.getElementById("form-cadastro");

// VERIFICA SE O FORMULÁRIO DE CADASTRO EXISTE NA PÁGINA
if(formCadastro){

    // ADICIONA UM EVENTO QUANDO O FORMULÁRIO FOR ENVIADO
    formCadastro.addEventListener("submit", function(event){

        // IMPEDE O ENVIO PADRÃO DO FORMULÁRIO
        event.preventDefault();

        // MOSTRA UMA MENSAGEM DE SUCESSO
        alert("Cadastro realizado com sucesso!");

        // REDIRECIONA PARA A PÁGINA DE LOGIN
        window.location.href = "login.html";

    });

}


// CONTADOR DE EVENTOS

// PEGA TODOS OS CARDS DE EVENTOS
const eventos = document.querySelectorAll(".card-evento");

// PEGA O ELEMENTO ONDE SERÁ MOSTRADO O TOTAL DE EVENTOS
const contador = document.getElementById("contador");

// VERIFICA SE O CONTADOR EXISTE NA PÁGINA
if(contador){

    // MOSTRA A QUANTIDADE TOTAL DE EVENTOS
    contador.textContent =
        `Total de eventos: ${eventos.length}`;

}


// BOTÕES DE INSCRIÇÃO

// PEGA TODOS OS BOTÕES DE INSCRIÇÃO
const botoes = document.querySelectorAll(".btn-inscricao");

// PERCORRE TODOS OS BOTÕES DE INSCRIÇÃO
botoes.forEach(botao => {

    // ADICIONA UM EVENTO DE CLIQUE EM CADA BOTÃO
    botao.addEventListener("click", () => {

        // PEGA O NOME DO EVENTO DO BOTÃO
        const evento = botao.dataset.evento;

        // PEGA A DATA DO EVENTO DO BOTÃO
        const data = botao.dataset.data;

        // SALVA O NOME DO EVENTO NO LOCALSTORAGE
        localStorage.setItem("evento", evento);

        // SALVA A DATA DO EVENTO NO LOCALSTORAGE
        localStorage.setItem("data", data);

        // REDIRECIONA PARA A PÁGINA DE INSCRIÇÃO
        window.location.href = "inscricao.html";

    });

});


// PREENCHER DADOS NA PÁGINA DE INSCRIÇÃO

// PEGA O ELEMENTO ONDE VAI APARECER O NOME DO EVENTO
const nomeEvento =
    document.getElementById("nome-evento");

// PEGA O ELEMENTO ONDE VAI APARECER A DATA DO EVENTO
const dataEvento =
    document.getElementById("data-evento");

// VERIFICA SE O ELEMENTO DO NOME DO EVENTO EXISTE
if(nomeEvento){

    // COLOCA O NOME DO EVENTO SALVO NO LOCALSTORAGE
    nomeEvento.textContent =
        localStorage.getItem("evento");

}

// VERIFICA SE O ELEMENTO DA DATA DO EVENTO EXISTE
if(dataEvento){

    // COLOCA A DATA DO EVENTO SALVA NO LOCALSTORAGE
    dataEvento.textContent =
        localStorage.getItem("data");

}


// FORMULÁRIO DE INSCRIÇÃO

// PEGA O FORMULÁRIO DE INSCRIÇÃO PELO ID
const formInscricao =
    document.getElementById("form-inscricao");

// VERIFICA SE O FORMULÁRIO DE INSCRIÇÃO EXISTE NA PÁGINA
if(formInscricao){

    // ADICIONA UM EVENTO QUANDO O FORMULÁRIO FOR ENVIADO
    formInscricao.addEventListener(
        "submit",
        function(event){

            // IMPEDE O ENVIO PADRÃO DO FORMULÁRIO
            event.preventDefault();

            // PEGA O NOME DO ALUNO DIGITADO NO CAMPO
            const nomeAluno =
                document.getElementById("nome").value;

            // SALVA O NOME DO ALUNO NO LOCALSTORAGE
            localStorage.setItem(
                "aluno",
                nomeAluno
            );

            // REDIRECIONA PARA A PÁGINA DE CONFIRMAÇÃO
            window.location.href =
                "confirmacao.html";

        }
    );

}


// PÁGINA DE CONFIRMAÇÃO

// PEGA O ELEMENTO ONDE VAI APARECER O EVENTO CONFIRMADO
const eventoConfirmado =
    document.getElementById("evento-confirmado");

// PEGA O ELEMENTO ONDE VAI APARECER A DATA CONFIRMADA
const dataConfirmada =
    document.getElementById("data-confirmada");

// PEGA O ELEMENTO ONDE VAI APARECER O ALUNO CONFIRMADO
const alunoConfirmado =
    document.getElementById("aluno-confirmado");

// VERIFICA SE O ELEMENTO DO EVENTO CONFIRMADO EXISTE
if(eventoConfirmado){

    // MOSTRA O EVENTO SALVO NO LOCALSTORAGE
    eventoConfirmado.textContent =
        localStorage.getItem("evento");

}

// VERIFICA SE O ELEMENTO DA DATA CONFIRMADA EXISTE
if(dataConfirmada){

    // MOSTRA A DATA SALVA NO LOCALSTORAGE
    dataConfirmada.textContent =
        localStorage.getItem("data");

}

// VERIFICA SE O ELEMENTO DO ALUNO CONFIRMADO EXISTE
if(alunoConfirmado){

    // MOSTRA O ALUNO SALVO NO LOCALSTORAGE
    alunoConfirmado.textContent =
        localStorage.getItem("aluno");

}