export const infiniteChar = "\u221e";

export function qtyToNumber(svalue: string): number {
    var value = svalue;
    if (value.startsWith(infiniteChar) && value.length > 1) {
        value = value.replace(infiniteChar, "");
    }
    let number = parseFloat(value);
    return(isNaN(number) || number === null) ? Number.MAX_SAFE_INTEGER : number;
}

export function numberToQtyStr(number: number, suffix: string = ""): string {
    if (isNaN(number) || number === undefined) {
        return ""
    }
    return isInfinite(number) ? infiniteChar : Math.round(number).toString() + suffix;
}

export function isInfinite(number: number) {
    return number === Number.MAX_SAFE_INTEGER;
}

export const titleCase = (str: string):string => {
    let res = str.toLowerCase().replace(/([-',\s^]+)([A-z])/g, (match, p1, p2) => p1+p2.toUpperCase())
    return res.charAt(0).toUpperCase() + res.slice(1);
};
