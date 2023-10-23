import * as React from 'react';
import {Component} from "react";

export interface TranslatedMessage {
    it: string,
    en: string
}
const msg = {
    intro: { it: "Questo calcolatore ti permette di miscelare acque diverse utilizzando anche additivi minerali (sali) " +
            "per ottenere l'acqua più vicina al tuo obiettivo, ti aiuta a calcolare ad esempio come mischiare l'acqua del " +
            "supermercato con quella del rubinetto per ottenere il profilo desiderato; potresti anche scoprire che combinando" +
            " tre diverse acque del supermercato non ti serve alcuna aggiunta di minerali, magari difficili da reperire.",
        en: "This calculator allows you to mix different waters also using mineral additives (salts) to get the water " +
            "closest to your target, it helps you calculate, for example, how to mix supermarket water with tap water " +
            "to obtain the desired profile; you may also find that by combining three different supermarket waters " +
            "you do not need any addition of minerals, often difficult to find"},
    intro1: {
        it: "Per prima cosa inserisci quanta acqua ti serve e il suo profilo, ossia il suo contenuto di minerali. Il profilo ideale cambia a seconda della " +
            "birra che vuoi brassare, io per sceglierlo di solito uso ",
        en: "First enter how much water you need and its profile, i.e. its mineral content. " +
            "The ideal profile changes according to the beer you want to brew, I usually rely on "},
    intro1b: {
        it: "Le quantità dei minerali sono da inserire in mg/L (ppm)",
        en: "Mineral quantities are to be entered in mg/L (ppm)",
    },
    intro2: {
        it: "Una volta selezionato il tuo obiettivo devi elencare le acque che hai a disposizione, " +
            "io ad esempio seleziono quelle presenti nel supermercato vicino casa, oltre all'acqua di rete." +
            " Se non specifichi i litri viene considerata una fonte illimitata (indicato dal simbolo ∞), se invece hai " +
            "solo pochi litri a disposizione puoi specificarlo. I dati dell'acqua di rete sono solitamente pubblici, a volte" +
            " basta richiederli al proprio Comune.",
        en: "Once you have selected your target, you must list the waters you have available, for example, " +
            "I select the ones present in the supermarket near my home, in addition to my tap water. " +
            "If you do not specify the liters, it is considered an unlimited source (indicated by the symbol ∞), if instead you " +
            "have only a few liters available you can specify it. The tap water data are usually public, " +
            "you may need to request them from your Municipality"
    },
    intro3: {
        it: "Dopo aver inserito tutte le acque disponibili, devi selezionare i sali che vuoi usare, anche in questo " +
            "caso puoi fare a meno di inserire la quantità massima di utilizzo, però è importante che selezioni i sali" +
            " che hai a disposizione, in modo che il calcolatore ne tenga conto nella ricetta.",
        en: "After entering all the available waters, you must select the salts you want to use, even in this case you " +
            "can do without entering the maximum amount of use, however it is important that you select the salts you " +
            "have available, so that the calculator will take into account in the recipe."
    },
    intro4: {
        it: "Clicca il bottone per cercare la combinazione migliore, potrai vedere le quantità dei vari " +
            "ingredienti da usare, il profilo finale della ricetta e la differenza " +
            "rispetto all'obiettivo che avevi impostato.",
        en: "Click the button to find the best combination, you will be able to see the quantities of the " +
            "various ingredients to be used, the final profile of the recipe and the difference " +
            "compared to the target profile you had set."
    },
    intro5: {
        it: "Se hai domande o suggerimenti mi puoi scrivere a rabdo@alebolo.33mail.com",
        en: "If you have questions or suggestions you can write me at rabdo@alebolo.33mail.com "
    },
    addWater: { it: "Aggiungi un'acqua commerciale", en: "Select an italian water"},
    addCustomWater: { it: "Aggiungi un'acqua", en: "Add another water source"},
    addSalt: { it: "Aggiungi dei sali", en: "Add salt"},
    welcome: { it: "Benvenuto", en: "Welcome", },
    searching: { it: "Creazione in corso...", en: "Searching in progress...", },
    find: { it: "Crea la ricetta", en: "Find the best combination", },
    subtitle: { it: "Brewing Water Profile Calculator", en: "Brewing Water Profile Calculator" },
    target: { it: "Obiettivo", en: "Target" },
    liters: { it: "litri", en: "liters"},
    calcium: { it: "calcio", en: "calcium"},
    magnesium: { it: "magnesio", en: "magnesium"},
    bicarbonates: { it: "bicarbonati", en: "bicarbonates"},
    sodium: { it: "sodio", en: "sodium"},
    chloride: { it: "cloruri", en: "chloride"},
    sulfate: { it: "solfati", en: "sulfate"},
    availableWaters: {it: "Acque Disponibili", en: "Available Waters"},
    availableSalts: {it: "Sali Disponibili", en: "Available Salts"},
    baseWater: {it: "Acqua Base", en: "Base Water"},
    salt: {it: "Sale", en: "Salt"},
    decigrams: { it: "Decigrammi", en: "Decigrams"},
    grams: { it: "Grammi", en: "Grams"},
    clearAll: { it: "Azzera tutto", en: "Clear All"},
    custom: { it: "Personalizzata", en: "Custom"},
    name: { it: "Nome", en: "Name"},
    select: { it: "Seleziona...", en: "Select..."},
    solutionIngredients: { it: "Ingredienti della Soluzione", en: "Solution Ingredients"},
    totals: { it: "Totali", en: "Totals"},
    delta: { it: "Distanza dall'obiettivo", en: "Distance from target"},
    recipe: { it: "Ricetta", en: "Recipe"},
    tableSalt: { it: "Sale da cucina", en: "Table Salt"},
    gypsum: { it: "Gypsum", en: "Gypsum"},
    epsomSalt: { it: "Solfato di Magnesio (Sali di Epsom)", en: "Epsom Salt"},
    calciumChloride: { it: "Cloruro di Calcio", en: "Calcium Chloride"},
    bakingSoda: { it: "Bicarbonato di Sodio", en: "Baking Soda"},
    chalk: { it: "Chalk", en: "Chalk"},
    picklingLime: { it: "Pickling Lime", en: "Pickling Lime"},
    magnesiumChloride: { it: "Cloruro di Magnesio", en: "Magnesium Chloride"},
    infoSolutionIngredients: {
        it: "Queste sono le quantità ideali per creare l'acqua più vicina possibile al tuo obiettivo, " +
            "se un ingrediente tra quelli che hai selezionato non è presente nella ricetta vuol dire che " +
            "non era utile nel raggiungere il profilo obiettivo",
        en: "These are the optimal quantities to create the water as close as possible to your goal, " +
            "if one of the ingredients you have selected is not present in the recipe, it means that " +
            "it was not useful in reaching the target profile"
    },
    infoTotal: {
        it: "Questo è il profilo dell'acqua creata miscelando gli ingredienti nelle quantità riportate qui sopra",
        en: "This is the profile of the water created by mixing the ingredients in the quantities shown above"
    },
    infoDelta: {
        it: "Di seguito è riportata la differenza tra il profilo obiettivo e quello trovato dal calcolatore",
        en: "The difference between the target profile and that found by the calculator is shown below"
    }
};

interface FlagProps {
    selected: boolean
    onClick: () => void
}

export class FlagIta extends Component<FlagProps, {}> {
    render() {
        let style = this.props.selected ? {borderBottom: "1px solid #999"} : {};
        return (
        <svg version="1.1" id="Layer_1"  x="0px" y="0px" viewBox="0 0 512 512"
             width="24" height="24" style={style} onClick={this.props.onClick}
        >
        <path fill="#73AF00" d="M170.667,423.724H8.828c-4.875,0-8.828-3.953-8.828-8.828V97.103c0-4.875,3.953-8.828,8.828-8.828
        h161.839V423.724z"/>
        <rect x="170.67" y="88.276" fill="#F5F5F5" width="170.67" height="335.448"/>
        <path fill="#FF4B55" d="M503.172,423.724H341.333V88.276h161.839c4.875,0,8.828,3.953,8.828,8.828v317.793
        C512,419.772,508.047,423.724,503.172,423.724z"/>
        </svg>
        )
    }
}

export class FlagEn extends Component<FlagProps, {}> {
    render() {
        let style = this.props.selected ? {borderBottom: "1px solid #999"} : {};
        return (
<svg version="1.1" id="Layer_1"  x="0px" y="0px"
	 viewBox="0 0 512.001 512.001"
     width="24" height="24" style={style} onClick={this.props.onClick}
>
<path fill="#41479B" d="M503.172,423.725H8.828c-4.875,0-8.828-3.953-8.828-8.828V97.104c0-4.875,3.953-8.828,8.828-8.828
	h494.345c4.875,0,8.828,3.953,8.828,8.828v317.793C512,419.772,508.047,423.725,503.172,423.725z"/>
<path fill="#F5F5F5" d="M512,97.104c0-4.875-3.953-8.828-8.828-8.828h-39.495l-163.54,107.147V88.276h-88.276v107.147
	L48.322,88.276H8.828C3.953,88.276,0,92.229,0,97.104v22.831l140.309,91.927H0v88.276h140.309L0,392.066v22.831
	c0,4.875,3.953,8.828,8.828,8.828h39.495l163.54-107.147v107.147h88.276V316.578l163.54,107.147h39.495
	c4.875,0,8.828-3.953,8.828-8.828v-22.831l-140.309-91.927H512v-88.276H371.691L512,119.935V97.104z"/>
<g>
	<polygon fill="#FF4B55" points="512,229.518 282.483,229.518 282.483,88.276 229.517,88.276 229.517,229.518 0,229.518
		0,282.483 229.517,282.483 229.517,423.725 282.483,423.725 282.483,282.483 512,282.483 	"/>
	<path fill="#FF4B55" d="M178.948,300.138L0.25,416.135c0.625,4.263,4.14,7.59,8.577,7.59h12.159l190.39-123.586H178.948z"/>
	<path fill="#FF4B55" d="M346.388,300.138H313.96l190.113,123.404c4.431-0.472,7.928-4.09,7.928-8.646v-7.258
		L346.388,300.138z"/>
	<path fill="#FF4B55" d="M0,106.849l161.779,105.014h32.428L5.143,89.137C2.123,90.54,0,93.555,0,97.104V106.849z"/>
	<path fill="#FF4B55" d="M332.566,211.863L511.693,95.586c-0.744-4.122-4.184-7.309-8.521-7.309h-12.647L300.138,211.863 H332.566z"/>
</g>
</svg>
    )
    }
}


export default msg
