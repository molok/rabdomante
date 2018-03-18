export interface WaterDef {
    l: number,
    ca: number,
    mg: number,
    na: number,
    so4: number,
    cl: number,
    hco3: number
}

export function water(name: string = "",
               l: number = 0,
               ca: number = 0,
               mg: number = 0,
               na: number = 0,
               so4: number = 0,
               cl: number = 0,
               hco3: number = 0
) {
    return {
        name: name,
        l: l,
        ca: ca,
        mg: mg,
        na: na,
        so4: so4,
        cl: cl,
        hco3: hco3,
    }
}

