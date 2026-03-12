let contador = 4;
// essa função adiciona uma linha de tarefa a mais
function adicionarHora() {
    const container = document.getElementById('container-tasks');
    const novaLinha = document.createElement('div');
    novaLinha.className = 'line-tasks';


    novaLinha.innerHTML = `
        <div class="input-group">
            <label class="form-label">código de parada</label>
            <select class="form-control-option" name="itens[${contador}].codigoParada">
                <option value="" disabled selected>Selecione...</option>
                <option value="1A">1A - Apoio a Outros Sites</option>
                <option value="1B">1B - Atividades Rentáveis Cliente</option>
                <option value="1C">1C - Atividades de Garantia Brasil</option>
                <option value="1D">1D - Atividades de Garantia Fábrica</option>
                <option value="1E">1E - Atividades de Concessão</option>
                <option value="1Ex">1Ex - Exame Periódicos</option>
                <option value="2">2 - Atestado / Licenças</option>
                <option value="2.1">2.1 - Atestado Corona Vírus</option>
                <option value="3">3 - Afastamento INSS</option>
                <option value="4">4 - Férias</option>
                <option value="5">5 - Licença Maternidade</option>
                <option value="42">42 - Cumprimento de Interstício</option>
                <option value="6">6 - Banco De Horas</option>
                <option value="7">7 - Mobilização</option>
                <option value="8">8 - DDS</option>
                <option value="9">9 - Preenchimento Art</option>
                <option value="10">10 - ASO não Liberado - Ag. Aprovação PCMSO</option>
                <option value="11">11 - Aguardando Programação de Serviço</option>
                <option value="12">12 - Aguardando Roteiro</option>
                <option value="13">13 - Aguardando Engenharia</option>
                <option value="14">14 - Aguardando Liberação Qualidade</option>
                <option value="15">15 - Aguardando Entrega EPI</option>
                <option value="16">16 - Aguardando Entrega Almoxarifado</option>
                <option value="17">17 - Aguardando Empilhadeira</option>
                <option value="18">18 - Aguardando Ferramenta</option>
                <option value="19">19 - Aguardando Movimentação De Carga / Atraso na chegada do equipamento</option>
                <option value="20">20 - Aguardando Liberação Segurança</option>
                <option value="21">21 - Aguardando Ponte</option>
                <option value="22">22 - Aguardando Peça</option>
                <option value="23">23 - Aguardando Mão de Obra</option>
                <option value="24">24 - Aguardando Parada de Máquina</option>
                <option value="25">25 - Aguardando Munck / Operação Munk</option>
                <option value="26">26 - Aguardando Guindaste</option>
                <option value="27">27 - Conferência de Peça Nova</option>
                <option value="28">28 - Elaboração, Manutenção, e/ou Inspeções de Dispositivo, Peça (não rentável) ou ferramenta</option>
                <option value="29">29 - Falta De Energia</option>
                <option value="30">30 - Falta De Água</option>
                <option value="31">31 - Inspeção De Ferramentas</option>
                <option value="32">32 - Inventário / Auditoria campo e EHS</option>
                <option value="33">33 - Limpeza E/Ou Organização</option>
                <option value="34">34 - Manutenção Equipamentos/ Infra estrutura</option>
                <option value="35">35 - Movimentação de Carga Não Produtiva</option>
                <option value="36">36 - Parada Chuva / Mau tempo</option>
                <option value="37">37 - Parada Sol / Descanso</option>
                <option value="38">38 - Vestiário</option>
                <option value="39">39 - Reunião</option>
                <option value="40">40 - Treinamento Técnico</option>
                <option value="41">41 - Treinamento Segurança Do Trabalho</option>
                <option value="43">43 - Elaboração de Relatório</option>
                <option value="44">44 - Inspeção de Máquinas</option>
                <option value="45">45 - OFICINA (empréstimo de funcionários)</option>
                <option value="46">46 - Atividades de Apoio Campo</option>
                <option value="47">47 - Recebimento Componentes Produtivos</option>
                <option value="48">48 - Crachá Bloqueado</option>
                <option value="49">49 - Acompanhando Cliente/Visitantes</option>
                <option value="50">50 - Paralisação Cliente</option>
                <option value="51">51 - Deslocamento/Viagens</option>
                <option value="52">52 - Suspeita Corona Vírus</option>
                <option value="53">53 - Banco de horas - Grupo de Risco</option>
                <option value="54">54 - Liberação do cliente - Corona Vírus</option>
                <option value="55">55 - Abono Chefia</option>
                <option value="56">56 - Falta sem justificativa</option>
                <option value="57">57 - Compensação Feriado / Folga - Komatsu</option>
            </select>
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