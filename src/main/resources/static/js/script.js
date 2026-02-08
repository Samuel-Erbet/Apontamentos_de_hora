let contador = 4;

function adicionarHora() {
    const container = document.getElementById('container-tasks');
    const novaLinha = document.createElement('div');
    novaLinha.className = 'line-tasks';


    novaLinha.innerHTML = `
        <div class="input-group">
            <label class="form-label">código de parada</label>
            <select class="form-control-option" name="itens[${contador}].codigoParada">
                <option value="" selected>Selecione...</option>
                <option value="2">2 - Atestado</option>
                <option value="1b">1b - Atividade Rentável</option>
            </select>
        </div>
        <div class="input-group">
            <label class="form-label">Descrição</label>
            <input class="form-control" type="text" name="itens[${contador}].descricao">
        </div>
        <div class="input-group">
            <label class="form-label">Início</label>
            <input class="form-control" type="time" name="itens[${contador}].horarioInicio">
        </div>
        <div class="input-group">
            <label class="form-label">Fim</label>
            <input class="form-control" type="time" name="itens[${contador}].horarioFim">
        </div>
    `;

    container.appendChild(novaLinha);
    contador++;
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

