export interface WaterDef {
    name: string,
    l: number,
    ca: number,
    mg: number,
    na: number,
    so4: number,
    cl: number,
    hco3: number
    visible: boolean
}

export function water(
               name: string = "",
               l: number = 0,
               ca: number = 0,
               mg: number = 0,
               na: number = 0,
               so4: number = 0,
               cl: number = 0,
               hco3: number = 0,
               visible: boolean = true
) {
    return { name, l, ca, mg, na, so4, cl, hco3, visible }
}

