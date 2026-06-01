// LOGIN

const formLogin = document.getElementById("form-login");

if(formLogin){

    formLogin.addEventListener("submit", function(event){

        event.preventDefault();

        window.location.href = "eventos.html";

    });

}


// CADASTRO

const formCadastro = document.getElementById("form-cadastro");

if(formCadastro){

    formCadastro.addEventListener("submit", function(event){

        event.preventDefault();

        alert("Cadastro realizado com sucesso!");

        window.location.href = "login.html";

    });

}


// CONTADOR DE EVENTOS

const eventos = document.querySelectorAll(".card-evento");

const contador = document.getElementById("contador");

if(contador){

    contador.textContent =
    `Total de eventos: ${eventos.length}`;

}


// BOTÕES DE INSCRIÇÃO

const botoes = document.querySelectorAll(".btn-inscricao");

botoes.forEach(botao => {

    botao.addEventListener("click", () => {

        const evento = botao.dataset.evento;

        const data = botao.dataset.data;

        localStorage.setItem("evento", evento);

        localStorage.setItem("data", data);

        window.location.href = "inscricao.html";

    });

});


// PREENCHER DADOS NA PÁGINA DE INSCRIÇÃO

const nomeEvento =
document.getElementById("nome-evento");

const dataEvento =
document.getElementById("data-evento");

if(nomeEvento){

    nomeEvento.textContent =
    localStorage.getItem("evento");

}

if(dataEvento){

    dataEvento.textContent =
    localStorage.getItem("data");

}


// FORMULÁRIO DE INSCRIÇÃO

const formInscricao =
document.getElementById("form-inscricao");

if(formInscricao){

    formInscricao.addEventListener(
        "submit",
        function(event){

            event.preventDefault();

            const nomeAluno =
            document.getElementById("nome").value;

            localStorage.setItem(
                "aluno",
                nomeAluno
            );

            window.location.href =
            "confirmacao.html";

        }
    );

}


// PÁGINA DE CONFIRMAÇÃO

const eventoConfirmado =
document.getElementById("evento-confirmado");

const dataConfirmada =
document.getElementById("data-confirmada");

const alunoConfirmado =
document.getElementById("aluno-confirmado");

if(eventoConfirmado){

    eventoConfirmado.textContent =
    localStorage.getItem("evento");

}

if(dataConfirmada){

    dataConfirmada.textContent =
    localStorage.getItem("data");

}

if(alunoConfirmado){

    alunoConfirmado.textContent =
    localStorage.getItem("aluno");

}