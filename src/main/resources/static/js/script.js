let contador = 4;
// essa função adiciona uma linha de tarefa a mais
function adicionarHora() {
    const container = document.getElementById('container-tasks');
    const novaLinha = document.createElement('div');
    novaLinha.className = 'line-tasks';


    novaLinha.innerHTML = `
        <div class="input-group">
            <label class="form-label">código de parada</label>
            <input class="form-control" type="text" name="itens[${contador}].codigoParada">
            <span class="error-msg" style="color: red; font-size: 0.7rem;"></span>
        </div>
        <div class="input-group">
            <label class="form-label">Numero da Os</label>
            <input class="form-control" type="text" name="itens[${contador}].numeroOs">
            <span class="error-msg" style="color: red; font-size: 0.7rem; display: block;"></span>
        </div>
        <div class="input-group">
            <label class="form-label">Descrição</label>
            <input class="form-control" type="text" name="itens[${contador}].descricao">
            <span class="error-msg" style="color: red; font-size: 0.7rem; display: block;"></span>
        </div>
        <div class="input-group">
            <label class="form-label">Início</label>
            <input class="form-control" type="time" name="itens[${contador}].horarioInicio">
            <span class="error-msg" style="color: red; font-size: 0.7rem; display: block;"></span>
        </div>
        <div class="input-group">
            <label class="form-label">Fim</label>
            <input class="form-control" type="time" name="itens[${contador}].horarioFim">
            <span class="error-msg" style="color: red; font-size: 0.7rem; display: block;"></span>
        </div>

        <button class="lineButtonAdd" type="button" onclick="adicionarHora()">adicionar</button>
        <button class="lineButtonRemove" type="button" onclick="removerHora(this)">remover</button>
    `;

    container.appendChild(novaLinha);
    contador++;
}

// remove a linha de tarefa que você clicou
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

// reorganiza o numero de linhas de tarefa baseado no seu indice
// sem ele daria erro, porque o spring passa os objetos através de um indice, se estivesse fora de ordem causaria um erro
function reordenarIndices() {
    let linhas = document.querySelectorAll(".line-tasks");
    linhas.forEach((linha, index) => {
        const elementos = linha.querySelectorAll("input, select, textarea");
        elementos.forEach(el => {
            let nameOriginal = el.getAttribute("name");
            if (nameOriginal) {
                let novoName = nameOriginal.replace(/\[\d+\]/, `[${index}]`);
                el.setAttribute("name", novoName);
            }
        });
        linha.id = "linha-" + index;
    });
    contador = linhas.length;
}

// não vai permitir que o usuário envie o formulário se ele não passar nas condicionais
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
            const codigo = linha.querySelector("input[name*='.codigoParada']");
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

        // o user não pode ter menos que 4 linhas de tarefa
        if (!preencheuPeloMenosUm) {
            alert("Preencha pelo menos uma tarefa completa.");
            temErro = true;
        }

        // se ele não passar nas validações o user não conseguirá enviar o formulário
        if (temErro) {
            e.preventDefault();
            const primeiroErro = document.querySelector(".error-msg:not(:empty)");
            if (primeiroErro) primeiroErro.scrollIntoView({ behavior: 'smooth', block: 'center'});
        } else {

        // se for valido o formulário ele n vai poder clicar no botão de enviar de novo, para evitar enviar os
        // mesmos valores de novo
        const btn = document.querySelector(".button");
        btn.innerText = "Enviando...";
        btn.style.opacity = "0.5";
        btn.disabled = true;
        }
    };

});