// SELECIONA O BOTÃO HAMBÚRGUER
const hamburger = document.querySelector(".hamburger");

// SELECIONA A NAVEGAÇÃO
const nav = document.querySelector(".nav");

// ABRE E FECHA O MENU AO CLICAR NO BOTÃO HAMBÚRGUER
hamburger.addEventListener("click", () => nav.classList.toggle("active"));

// FECHA O MENU AO CLICAR EM QUALQUER LINK DA NAVEGAÇÃO
document.querySelectorAll(".nav a").forEach(link => {
    link.addEventListener("click", () => {
        nav.classList.remove("active");
    });
});


// SELECIONA O BOTÃO MANUAL DO SLIDER
var radio = document.querySelector('.manual-btn')

// CONTADOR DO SLIDER
var cont = 1

// MARCA O PRIMEIRO SLIDE COMO ATIVO
document.getElementById('radio1').checked = true

// TROCA A IMAGEM DO SLIDER A CADA 5 SEGUNDOS
setInterval(() => {
    proximaImg()
}, 5000)

// FUNÇÃO PARA PASSAR PARA A PRÓXIMA IMAGEM
function proximaImg(){
    cont++

    // VOLTA PARA A PRIMEIRA IMAGEM QUANDO PASSAR DA TERCEIRA
    if(cont > 3){
        cont = 1
    }

    // MARCA O RADIO CORRESPONDENTE AO SLIDE ATUAL
    document.getElementById('radio'+cont).checked = true
}