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


document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById("form");

    form.onsubmit = function(e) {
        let temErro = false;

        document.querySelectorAll(".error-msg").forEach(span => span.innerText = "");

        const unidadeInput = document.querySelector("[name='itens[0].unidade']");
        const dataInput = document.querySelector("[name='itens[0].data']");

        if (!unidadeInput.value.trim()) {
            unidadeInput.nextElementSibling.innerText = "Unidade obrigatória";
            temErro = true;
        }
        if (!dataInput.value) {
            dataInput.nextElementSibling.innerText = "Data obrigatória";
            temErro = true;
        }

        let linhas = document.querySelectorAll(".line-tasks");
        let preencheuPeloMenosUm = false;

        linhas.forEach((linha) => {
            const codigo = linha.querySelector("select");
            const os = linha.querySelector("input[name*='.numeroOs']");
            const desc = linha.querySelector("input[name*='.descricao']");
            const inicio = linha.querySelector("input[name*='.horarioInicio']");
            const fim = linha.querySelector("input[name*='.horarioFim']");

            if (codigo.value) {
                preencheuPeloMenosUm = true;

                if (!desc.value.trim()) {
                    desc.nextElementSibling.innerText = "Informe a descrição";
                    temErro = true;
                }
                if (!inicio.value) {
                    inicio.nextElementSibling.innerText = "Início vazio";
                    temErro = true;
                }
                if (!fim.value) {
                    fim.nextElementSibling.innerText = "Fim vazio";
                    temErro = true;
                } else if (inicio.value && inicio.value >= fim.value) {
                    fim.nextElementSibling.innerText = "Fim deve ser maior";
                    temErro = true;
                }
                if (codigo.value === "1b" && !os.value.trim()) {
                    os.nextElementSibling.innerText = "OS obrigatória";
                    temErro = true;
                }
            }
        });

        if (!preencheuPeloMenosUm) {
            alert("Preencha pelo menos uma tarefa completa.");
            temErro = true;
        }

        if (temErro) {
            e.preventDefault();
            const primeiroErro = document.querySelector(".error-msg:not(:empty)");
            if (primeiroErro) primeiroErro.scrollIntoView({ behavior: 'smooth', block: 'center' });
        }
    };
});