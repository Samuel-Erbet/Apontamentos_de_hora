let contador = 3;
function adicionarHora(){
    let container = document.getElementById("container-tasks");
    let primeiraLinha = document.querySelector(".line-tasks");
    let novaLinha = primeiraLinha.cloneNode(true);

    const inputs = novaLinha.querySelectorAll("input");
    inputs.forEach(input => {
        input.value = "";
    });

    container.appendChild(novaLinha);
    reordenarIndices();
}


function removerHora(botton) {
    let linha = botton.closest(".line-tasks");
    let container = document.getElementById("container-tasks");

    if (container.querySelectorAll(".line-tasks").length > 4) {
        linha.remove();
        reordenarIndices();
    } else {
        alert("Você deve ter pelo menos 4 tarefas.");
    }
}


function reordenarIndices() {
    let container = document.getElementById("container-tasks");
    let linhas = container.querySelectorAll(".line-tasks");

    linhas.forEach((linha, index) => {
        const inputs = linha.querySelectorAll("input");
        inputs.forEach(input => {
            let nameOriginal = input.getAttribute("name");
            let novoName = nameOriginal.replace(/\[\d+\]/, `[${index}]`);
            input.setAttribute("name", novoName);
        });
        linha.id = "linha-" + index;
    });
    contador = linhas.length;
}

