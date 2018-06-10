import * as React from 'react';
import {Component} from 'react';
import {TranslatedMessage} from '../i18n/msg'

interface TranslateProps {
    msg: TranslatedMessage
}

export class TranslateConf {
    public static usrLang:string|null|undefined = null;
}

export const translate = (msg:TranslatedMessage) => {
    let lang;
    if (TranslateConf.usrLang) {
        lang = TranslateConf.usrLang;
    } else {
        lang = navigator.language.split("-")[0];
    }

    if (!lang) {
        lang = "en";
    }

    var res = msg[lang];
    return res ? res : msg + " (translation missing)";
}

class Translate extends Component<TranslateProps, {}> {
    render() {
       return(
            <>
                {translate(this.props.msg)}
           </>
       )
    }
}

export default Translate