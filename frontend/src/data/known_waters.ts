import {titleCase} from "../components/utils";

const KNOWN_WATERS = [
      { name: "Acquedotto Milano Sud-Est", ca: 84, mg: 19, na: 15.4, so4: 50.7, cl: 29.2, hco3: 217 }
    , { name: "SANT'ANNA Rebruant", ca: 1.6, mg: -1, na: 1.9, so4: 3.4, cl: -1, hco3: 6.0 }
    , { name: "SANT'ANNA DI VINADIO", ca: 10.5, mg: -1, na: 0.9, so4: 7.8, cl: -1, hco3: 26.2 }
    , { name: "Blues Vinadio", ca: 3.3, mg: 0.42, na: 1.5, so4: 3.3, cl: 0.25, hco3: 11 }
    , { name: "ABRAU", ca: 26.0, mg: 12.5, na: 0.8, so4: -1, cl: -1, hco3: 140.0 }
    , { name: "ACETOSELLA", ca: 370.0, mg: 68.0, na: 90.0, so4: -1, cl: -1, hco3: 1290.0 }
    , { name: "ACQUA ARVE'", ca: 596.0, mg: 143.0, na: 377.5, so4: 2010.0, cl: 302.0, hco3: 613.0 }
    , { name: "ACQUA DEL CARDINALE", ca: 39.3, mg: 0.51, na: 0.81, so4: 0.6, cl: 3.7, hco3: 122.0 }
    , { name: "ACQUA DEL FAIALLO FONTE ARGENTIERA", ca: 0.8, mg: 0.85, na: 2.85, so4: 0.8, cl: 3.1, hco3: 5.3 }
    , { name: "ACQUA DELLA MADONNA", ca: 367.0, mg: 69.0, na: 82.0, so4: -1, cl: -1, hco3: 1359.0 }
    , { name: "ACQUA DI NEPI", ca: 82.0, mg: 28.0, na: 27.0, so4: 38.0, cl: 19.0, hco3: 433.0 }
    , { name: "ACQUA DI TEMPIO", ca: 13.5, mg: 7.6, na: 37.0, so4: 18.0, cl: 54.71, hco3: -1 }
    , { name: "ACQUA MAGNA", ca: 50.5, mg: 7.54, na: 20.7, so4: 10.7, cl: 30.13, hco3: 167.75 }
    , { name: "ACQUA MELLIN", ca: 5.0, mg: 1.2, na: 0.9, so4: -1, cl: 0.4, hco3: -1 }
    , { name: "ACQUA PLINIANA", ca: 242.0, mg: 56.2, na: 18.0, so4: 676.7, cl: 7.0, hco3: 266.5 }
    , { name: "ACQUA SACRA", ca: 176.2, mg: 19.9, na: 56.0, so4: 66.5, cl: 42.5, hco3: 689.3 }
    , { name: "ACQUA SANTA DI CHIANCIANO", ca: 715.0, mg: 173.0, na: 25.5, so4: 1840.0, cl: 25.2, hco3: 842.0 }
    , { name: "ACQUA SILVA", ca: -1, mg: -1, na: 31.0, so4: 32.5, cl: 4.2, hco3: 119.0 }
    , { name: "ACQUABAIDA", ca: 99.8, mg: 33.8, na: 9.0, so4: 19.3, cl: 24.9, hco3: 183.0 }
    , { name: "ACQUAROSSA", ca: 120.2, mg: 150.9, na: 158.5, so4: -1, cl: 70.8, hco3: 1108.4 }
    , { name: "AEMILIA", ca: 76.1, mg: 71.4, na: 48.3, so4: 102.6, cl: 23.7, hco3: 542.7 }
    , { name: "AGNANO", ca: 384.7, mg: 75.2, na: 306.0, so4: 362.0, cl: 319.0, hco3: 1391.2 }
    , { name: "ALBA", ca: 10.0, mg: 1.8, na: 1.7, so4: 6.4, cl: 1.0, hco3: 29.0 }
    , { name: "ALISEA LEONARDO PRIMALUNA", ca: 13.2, mg: 2.5, na: 2.2, so4: 8.1, cl: 0.7, hco3: 42.7 }
    , { name: "ALLODOLA", ca: 390.7, mg: 145.9, na: 108.8, so4: 76.9, cl: 137.0, hco3: 1935.9 }
    , { name: "ALPI BIANCHE", ca: 12.5, mg: 0.5, na: 0.9, so4: 8.9, cl: -1, hco3: -1 }
    , { name: "ALPI COZIE", ca: -1, mg: 0.83, na: 2.1, so4: 4.0, cl: 1.6, hco3: -1 }
    , { name: "ALPIA", ca: 5.7, mg: 3.4, na: 3.5, so4: 4.7, cl: 1.1, hco3: 33.7 }
    , { name: "ALTE VETTE", ca: 11.8, mg: 0.6, na: 0.9, so4: 8.9, cl: -1, hco3: 29.5 }
    , { name: "AMATA", ca: 94.46, mg: 44.96, na: 21.42, so4: 6.13, cl: 31.66, hco3: 485.0 }
    , { name: "AMBRA", ca: 92.8, mg: 51.84, na: 48.6, so4: -1, cl: 67.35, hco3: 454.45 }
    , { name: "AMERINO", ca: 157.0, mg: 8.4, na: 11.5, so4: -1, cl: 17.8, hco3: 429.0 }
    , { name: "AMOROSA", ca: 0.97, mg: 0.6, na: 4.03, so4: 0.85, cl: 6.73, hco3: 4.3 }
    , { name: "ANGELICA", ca: 91.7, mg: 1.17, na: 3.06, so4: 1.84, cl: 5.62, hco3: 278.0 }
    , { name: "ANTICA FONTE RABBI", ca: 123.4, mg: 44.9, na: 510.0, so4: 6.8, cl: 232.0, hco3: 1670.0 }
    , { name: "ANTICA FONTE TARTAVALLE", ca: 521.4, mg: 99.1, na: 12.5, so4: 1516.0, cl: 106.0, hco3: 262.0 }
    , { name: "APPIA", ca: 140.14, mg: 28.65, na: 50.0, so4: 34.85, cl: 33.68, hco3: 646.6 }
    , { name: "AQUALIEVE", ca: 5.2, mg: 2.8, na: -1, so4: -1, cl: -1, hco3: -1 }
    , { name: "AQUA PARMALAT", ca: 19.0, mg: 2.0, na: 1.9, so4: -1, cl: -1, hco3: 20.0 }
    , { name: "ATTIVA", ca: 248.0, mg: 578.0, na: 13950.0, so4: 31020.0, cl: 50.3, hco3: 1015.0 }
    , { name: "AUGUSTA", ca: -1, mg: 43.0, na: 52.0, so4: 45.0, cl: -1, hco3: 390.0 }
    , { name: "AURORA", ca: 632.0, mg: 4.2, na: 5.4, so4: 1380.0, cl: 6.3, hco3: 423.0 }
    , { name: "AUSONIA", ca: 50.0, mg: 54.8, na: 23.2, so4: 70.0, cl: 10.4, hco3: 403.0 }
    , { name: "AZZURRA", ca: 78.4, mg: 32.0, na: 1.05, so4: 138.0, cl: 1.0, hco3: 224.0 }
    , { name: "BALDA", ca: 26.7, mg: 5.1, na: 3.6, so4: 17.0, cl: 2.0, hco3: 90.3 }
    , { name: "BEBER-SORGENTE DOPPIO", ca: 28.0, mg: 16.0, na: 0.5, so4: 5.0, cl: 0.9, hco3: 162.0 }
    , { name: "BERNINA", ca: 8.0, mg: 0.75, na: 0.93, so4: 6.5, cl: 0.2, hco3: 19.5 }
    , { name: "BOARIO", ca: 131.0, mg: 40.0, na: 5.0, so4: 240.0, cl: 4.0, hco3: 303.0 }
    , { name: "BORROMEA", ca: 41.0, mg: 6.6, na: 6.15, so4: 94.55, cl: 6.6, hco3: 58.3 }
    , { name: "BRACCA ANTICA FONTE", ca: 123.8, mg: 44.6, na: 20.0, so4: 265.0, cl: 29.5, hco3: 265.0 }
    , { name: "CALABRIA", ca: 10.8, mg: 4.3, na: 10.3, so4: 8.7, cl: 15.2, hco3: 45.7 }
    , { name: "FONTI BAUDA", ca: 4.7, mg: 1.0, na: 3.8, so4: 4.7, cl: 4.0, hco3: 16.0 }
    , { name: "CAMOREI", ca: 62.1, mg: 6.7, na: 4.9, so4: 15.3, cl: 6.5, hco3: 284.6 }
    , { name: "CANAY", ca: 1.8, mg: 1.62, na: 1.75, so4: 9.26, cl: 1.31, hco3: 5.43 }
    , { name: "CANCIULLE", ca: 126.9, mg: 4.8, na: 16.1, so4: 347.0, cl: 30.0, hco3: 359.1 }
    , { name: "CAPANNELLE", ca: 102.61, mg: 23.36, na: 37.0, so4: 27.3, cl: 35.45, hco3: 469.7 }
    , { name: "CAPPUCCINO", ca: 16.8, mg: 12.7, na: 351.6, so4: 7.9, cl: 1.2, hco3: 536.3 }
    , { name: "CASTELLINA", ca: 53.0, mg: 2.8, na: 2.2, so4: 2.2, cl: 3.5, hco3: 179.0 }
    , { name: "CASTELLO", ca: 64.2, mg: 32.7, na: 0.8, so4: 8.0, cl: 1.1, hco3: 349.0 }
    , { name: "CAVAGRANDE", ca: 10.8, mg: 8.5, na: 36.3, so4: 33.0, cl: 27.6, hco3: -1 }
    , { name: "CECILIANA", ca: 26.43, mg: 11.17, na: 6.6, so4: 3.0, cl: 21.27, hco3: 122.0 }
    , { name: "CERELIA", ca: 121.0, mg: -1, na: 5.9, so4: 7.6, cl: 5.6, hco3: 418.0 }
    , { name: "CERTOSA FONTE CAMARDA", ca: 6.2, mg: 4.12, na: 7.8, so4: 8.4, cl: 14.89, hco3: -1 }
    , { name: "CERTOSA FONTE PERNA", ca: 8.0, mg: 5.0, na: 4.0, so4: 7.0, cl: 17.5, hco3: -1 }
    , { name: "CERTOSA FONTE PIETRE BIANCHE", ca: 7.2, mg: 4.8, na: 7.1, so4: 6.1, cl: 7.8, hco3: -1 }
    , { name: "CERVA", ca: 23.8, mg: 0.4, na: 33.7, so4: 30.5, cl: 14.0, hco3: 131.4 }
    , { name: "CHIARELLA", ca: 40.6, mg: 22.1, na: 0.9, so4: 8.6, cl: 0.9, hco3: 217.8 }
    , { name: "CIAPPAZZI", ca: 118.0, mg: 107.0, na: 448.0, so4: 148.0, cl: 117.0, hco3: 1800.0 }
    , { name: "CIME BIANCHE", ca: 11.7, mg: 1.2, na: 1.4, so4: 8.1, cl: -1, hco3: -1 }
    , { name: "CINCIANO", ca: 273.6, mg: 46.8, na: 125.2, so4: 105.6, cl: 70.5, hco3: 1178.0 }
    , { name: "CINTOIA", ca: 10.5, mg: 6.0, na: 9.4, so4: 1.19, cl: 1.5, hco3: 33.2 }
    , { name: "CINZIA", ca: 92.0, mg: 8.4, na: 9.5, so4: 13.0, cl: 7.8, hco3: 317.259 }
    , { name: "CLAUDIA", ca: 86.0, mg: 21.0, na: 61.0, so4: 41.0, cl: 51.0, hco3: 484.0 }
    , { name: "COLLALLI", ca: 107.5, mg: 58.3, na: 1376.0, so4: 856.2, cl: 1851.0, hco3: 539.0 }
    , { name: "CONIANO", ca: 176.0, mg: 64.0, na: 668.0, so4: 179.0, cl: 348.1, hco3: 1836.6 }
    , { name: "CORALBA", ca: 54.0, mg: 21.0, na: 0.4, so4: 8.6, cl: 0.8, hco3: 255.0 }
    , { name: "CORINTHIA", ca: 211.6, mg: 67.8, na: 173.3, so4: 526.9, cl: 318.6, hco3: 261.8 }
    , { name: "CORONA", ca: 12.1, mg: 13.6, na: 31.8, so4: 7.0, cl: 33.7, hco3: 125.0 }
    , { name: "COTTORELLA", ca: 103.96, mg: 2.84, na: 4.99, so4: 7.09, cl: 12.55, hco3: 313.7 }
    , { name: "COURMAYEUR", ca: 579.0, mg: 59.0, na: 0.7, so4: 1447.0, cl: 0.4, hco3: 180.0 }
    , { name: "CRISTALIA'", ca: 44.0, mg: 3.5, na: 1.5, so4: 60.8, cl: -1, hco3: 78.4 }
    , { name: "CRISTALLO", ca: 3.9, mg: 4.2, na: 11.8, so4: 1.8, cl: 17.0, hco3: 18.3 }
    , { name: "CRODO LISIEL", ca: 56.4, mg: 6.5, na: 5.4, so4: 84.5, cl: 2.1, hco3: 109.0 }
    , { name: "CUTOLO RIONERO", ca: 50.0, mg: 13.1, na: 72.4, so4: 54.0, cl: 34.0, hco3: 298.0 }
    , { name: "NORDA", ca: 10.8, mg: 3.0, na: 2.3, so4: 6.3, cl: 0.6, hco3: 52.3 }
    , { name: "DIAMANTE", ca: 153.0, mg: 44.0, na: 730.0, so4: 377.4, cl: 333.23, hco3: 1684.1 }
    , { name: "DIPODI", ca: 40.96, mg: 20.7, na: 33.04, so4: 23.7, cl: 37.88, hco3: 224.48 }
    , { name: "DOLOMITI", ca: 23.8, mg: 8.7, na: 1.3, so4: 22.0, cl: 1.1, hco3: 94.6 }
    , { name: "DON CARLO", ca: 194.0, mg: 21.8, na: 16.9, so4: 13.5, cl: 23.0, hco3: 701.0 }
    , { name: "DONATA", ca: 198.8, mg: 71.9, na: 97.8, so4: 7.2, cl: 70.9, hco3: 110.2 }
    , { name: "DUCALE", ca: 13.4, mg: 1.4, na: 3.0, so4: 6.8, cl: 3.7, hco3: 40.0 }
    , { name: "EGERIA", ca: 93.5, mg: 23.6, na: 44.5, so4: 27.3, cl: 33.6, hco3: 491.2 }
    , { name: "EUREKA", ca: 66.5, mg: 30.1, na: 33.5, so4: -1, cl: 59.6, hco3: 311.0 }
    , { name: "FABIA", ca: 137.5, mg: 4.13, na: 17.03, so4: 28.4, cl: 23.7, hco3: 390.3 }
    , { name: "FABRIZIA", ca: 4.0, mg: 10.7, na: 10.88, so4: 8.0, cl: 14.2, hco3: 39.663 }
    , { name: "FAITO", ca: 73.0, mg: 18.0, na: 12.0, so4: 12.6, cl: 16.6, hco3: 311.1 }
    , { name: "FEDERICA", ca: 10.02, mg: 3.89, na: 85.0, so4: 18.8, cl: 73.7, hco3: 124.4 }
    , { name: "FELICIA", ca: 144.0, mg: 58.6, na: -1, so4: -1, cl: -1, hco3: 920.0 }
    , { name: "FERRARELLE", ca: 365.0, mg: 18.0, na: 49.0, so4: 4.0, cl: 19.0, hco3: 1430.0 }
    , { name: "FILETTE", ca: 77.4, mg: 1.4, na: 3.3, so4: 1.8, cl: 5.0, hco3: 238.0 }
    , { name: "FIUGGI", ca: 15.9, mg: 6.3, na: 6.4, so4: 6.0, cl: 13.9, hco3: 81.7 }
    , { name: "FLAMINIA", ca: 71.0, mg: 1.0, na: 2.6, so4: 2.2, cl: 5.3, hco3: 216.0 }
    , { name: "FLAVIA", ca: 59.4, mg: 26.9, na: 0.7, so4: 26.9, cl: 0.6, hco3: 288.0 }
    , { name: "FONTALBA", ca: -1, mg: 8.0, na: 12.0, so4: -1, cl: 15.6, hco3: 88.0 }
    , { name: "FONTANACCIO", ca: 102.2, mg: 2.7, na: 4.7, so4: 6.2, cl: 12.0, hco3: 311.0 }
    , { name: "FONTE ANNIA", ca: 53.0, mg: 24.9, na: 3.5, so4: 81.1, cl: 1.5, hco3: 189.0 }
    , { name: "FONTE AURA", ca: 132.4, mg: 6.1, na: 8.03, so4: 40.2, cl: 15.73, hco3: 368.2 }
    , { name: "FONTE AZZURRINA", ca: 14.5, mg: 1.0, na: 3.5, so4: 2.6, cl: 4.7, hco3: 43.6 }
    , { name: "FONTE BRUNELLA", ca: 11.2, mg: 3.6, na: 3.5, so4: 7.5, cl: 3.0, hco3: 51.5 }
    , { name: "FONTE CAUDANA", ca: 9.0, mg: 4.2, na: 2.6, so4: 3.7, cl: 1.8, hco3: 44.0 }
    , { name: "FONTE CORTE PARADISO", ca: 71.4, mg: 28.2, na: 3.1, so4: 61.5, cl: 4.6, hco3: 277.0 }
    , { name: "FONTE DE' MEDICI", ca: 40.0, mg: 17.0, na: 28.0, so4: 26.0, cl: 43.0, hco3: 175.0 }
    , { name: "FONTE DEL FARO", ca: 1134.8, mg: 9.3, na: 63.1, so4: 40.1, cl: 162.2, hco3: 312.7 }
    , { name: "FONTE DEL LUPO", ca: 3.2, mg: 1.4, na: 4.0, so4: 2.5, cl: 4.3, hco3: 8.9 }
    , { name: "FONTE DEL PARCO", ca: 75.0, mg: 7.5, na: 5.5, so4: 17.6, cl: 3.2, hco3: 258.2 }
    , { name: "FONTE DEL ROMITO", ca: 40.0, mg: 8.1, na: 11.2, so4: 7.6, cl: 13.5, hco3: 165.0 }
    , { name: "FONTE DELLA BUVERA", ca: 13.0, mg: 0.8, na: 0.9, so4: 6.9, cl: 0.9, hco3: 36.6 }
    , { name: "FONTE DELLA VIRTU'", ca: 5.2, mg: 0.9, na: 3.3, so4: 4.1, cl: 3.1, hco3: 15.9 }
    , { name: "FONTE DELLE ALPI", ca: -1, mg: 0.2, na: 1.3, so4: 2.1, cl: 0.4, hco3: -1 }
    , { name: "FONTE DELLE ROCCE", ca: 33.2, mg: 10.23, na: 20.22, so4: 11.02, cl: 31.9, hco3: 119.1 }
    , { name: "FONTE DI ALICE", ca: 57.2, mg: 23.1, na: 96.1, so4: 50.5, cl: 70.6, hco3: 363.0 }
    , { name: "FONTE DI PALME", ca: 11.3, mg: 1832.0, na: 2902.0, so4: 3.39, cl: 5203.0, hco3: 35.6 }
    , { name: "FONTE ELEONORA", ca: 27.0, mg: 10.4, na: 52.0, so4: 15.12, cl: 79.79, hco3: -1 }
    , { name: "FONTE ELISA", ca: 96.5, mg: 3.1, na: 10.6, so4: 19.8, cl: 18.0, hco3: 277.0 }
    , { name: "FONTE ILARIA", ca: 56.1, mg: 7.0, na: 11.0, so4: 7.5, cl: 18.3, hco3: 194.0 }
    , { name: "FONTE ITALA", ca: 48.05, mg: 12.63, na: -1, so4: 21.87, cl: 17.73, hco3: 305.0 }
    , { name: "FONTE LIDIA", ca: 4.6, mg: 2.8, na: 202.0, so4: 65.0, cl: 9.5, hco3: 455.0 }
    , { name: "FONTE LIETA", ca: 41.4, mg: 4.6, na: 10.0, so4: 30.9, cl: 3.7, hco3: 135.0 }
    , { name: "FONTE LONERA", ca: 25.6, mg: 12.0, na: 0.8, so4: 2.9, cl: 1.0, hco3: 130.0 }
    , { name: "FONTE MARGHERITA", ca: 225.0, mg: 131.0, na: 308.0, so4: 485.0, cl: 115.0, hco3: 1409.0 }
    , { name: "FONTE MOLINO ALBEDOSA", ca: 601.2, mg: 328.4, na: 3178.0, so4: 1792.7, cl: 5575.8, hco3: 145.8 }
    , { name: "FONTE NAPOLEONE", ca: 4.5, mg: 2.5, na: 16.0, so4: 5.9, cl: 27.8, hco3: 14.2 }
    , { name: "FONTE POMPEI", ca: 138.0, mg: 39.0, na: 185.0, so4: 123.0, cl: 42.0, hco3: -1 }
    , { name: "FONTE PREISTORICA", ca: 91.85, mg: 30.78, na: 8.027, so4: 30.73, cl: -1, hco3: 396.3 }
    , { name: "FONTE PRIMAVERA", ca: 89.2, mg: 17.5, na: 4.4, so4: 23.6, cl: 5.7, hco3: 329.0 }
    , { name: "FONTE REGINA", ca: 160.0, mg: 109.0, na: 288.0, so4: 94.0, cl: 6.3, hco3: 1726.0 }
    , { name: "FONTE SANTA CHIARA", ca: 100.0, mg: 18.0, na: 26.0, so4: 122.0, cl: -1, hco3: 230.0 }
    , { name: "FONTE SERENA", ca: 31.2, mg: 11.9, na: 6.5, so4: 13.4, cl: 5.4, hco3: 143.0 }
    , { name: "FONTE VILLA", ca: 23.1, mg: 13.0, na: 18.0, so4: 19.0, cl: 19.2, hco3: 122.0 }
    , { name: "FONTE VIVIA", ca: 84.0, mg: 33.0, na: 27.0, so4: 38.0, cl: 19.0, hco3: 502.0 }
    , { name: "FONTEALTA", ca: 4.8, mg: 0.2, na: 1.5, so4: 4.3, cl: 0.5, hco3: 18.4 }
    , { name: "FONTECHIARA", ca: 50.2, mg: 31.0, na: 76.0, so4: 123.3, cl: -1, hco3: 348.0 }
    , { name: "FONTEDORO", ca: 4.8, mg: 1.4, na: 6.0, so4: 4.2, cl: 10.0, hco3: 16.5 }
    , { name: "FONTELAURA", ca: 44.0, mg: 23.7, na: 1.6, so4: 9.9, cl: 1.4, hco3: 236.1 }
    , { name: "FONTEMURA", ca: 69.7, mg: 24.4, na: 14.8, so4: 54.6, cl: 10.2, hco3: 292.8 }
    , { name: "FONTENOCE", ca: 18.4, mg: 4.9, na: 5.5, so4: 8.9, cl: 8.0, hco3: 85.4 }
    , { name: "FONTENOVA", ca: 8.52, mg: 31.32, na: 5.18, so4: 12.2, cl: 3.2, hco3: 180.2 }
    , { name: "FONTEPATRI", ca: 74.8, mg: 27.2, na: 81.0, so4: 54.0, cl: 47.0, hco3: 421.0 }
    , { name: "FONTESANA", ca: 125.0, mg: 25.0, na: 44.0, so4: 69.0, cl: 47.0, hco3: 427.0 }
    , { name: "FONTEVIVA", ca: 7.8, mg: 2.0, na: 4.4, so4: 3.2, cl: 9.0, hco3: 32.9 }
    , { name: "FONTI DI CRODO VALLE D'ORO", ca: 502.0, mg: 54.0, na: 2.0, so4: 1363.0, cl: 0.6, hco3: 77.5 }
    , { name: "FRASASSI", ca: 99.5, mg: 4.3, na: 19.9, so4: 24.8, cl: 19.4, hco3: 299.0 }
    , { name: "FRISIA", ca: 16.2, mg: 2.5, na: 2.7, so4: 13.0, cl: 1.95, hco3: 48.8 }
    , { name: "FUCOLI", ca: 647.0, mg: 79.5, na: 18.2, so4: 1560.0, cl: 22.7, hco3: 425.0 }
    , { name: "FUNTE FRIA", ca: 26.0, mg: 7.2, na: 103.0, so4: 16.25, cl: 157.46, hco3: 99.15 }
    , { name: "FUTURA", ca: 37.0, mg: 12.6, na: 15.5, so4: 18.5, cl: 25.4, hco3: 207.4 }
    , { name: "GABINIA", ca: 23.5, mg: 11.11, na: 11.2, so4: 4.49, cl: 17.31, hco3: 119.1 }
    , { name: "GAIA", ca: 91.7, mg: 3.0, na: 19.5, so4: 28.0, cl: 19.9, hco3: 268.5 }
    , { name: "GAJUM", ca: 59.7, mg: 9.2, na: 1.0, so4: 12.0, cl: 1.2, hco3: 205.0 }
    , { name: "GALLO", ca: 95.09, mg: 32.6, na: 32.0, so4: 26.9, cl: 29.2, hco3: 457.64 }
    , { name: "GALVANINA", ca: 135.0, mg: 23.0, na: 36.0, so4: 68.0, cl: 37.0, hco3: 445.0 }
    , { name: "GARBARINO", ca: 19.0, mg: 0.5, na: 10.0, so4: -1, cl: -1, hco3: 69.0 }
    , { name: "GAUDENZIANA", ca: 26.4, mg: 2.2, na: 1.6, so4: 12.0, cl: 0.6, hco3: 75.6 }
    , { name: "GAUDIANELLO MONTICCHIO", ca: 158.0, mg: 58.0, na: -1, so4: 122.0, cl: 390.0, hco3: 989.0 }
    , { name: "GAVERINA", ca: 108.8, mg: 27.2, na: 6.9, so4: 118.5, cl: -1, hco3: 335.6 }
    , { name: "GENEROSA", ca: -1, mg: 40.8, na: 57.0, so4: -1, cl: -1, hco3: 550.9 }
    , { name: "GERACI", ca: 8.5, mg: 3.8, na: 6.4, so4: 25.0, cl: 14.1, hco3: 24.4 }
    , { name: "GERASIA", ca: 29.5, mg: 5.2, na: 14.5, so4: 36.7, cl: 17.4, hco3: 78.6 }
    , { name: "GIADA", ca: 166.0, mg: 42.0, na: 17.0, so4: 30.3, cl: 31.0, hco3: 680.0 }
    , { name: "GIARA", ca: 11.22, mg: 8.99, na: 54.0, so4: 14.39, cl: 73.14, hco3: 56.1 }
    , { name: "GIOIOSA DELLA VALSESIA", ca: 5.88, mg: 0.93, na: 4.22, so4: 6.1, cl: 2.0, hco3: 15.68 }
    , { name: "GIULIA", ca: 90.0, mg: 21.3, na: 53.0, so4: 47.0, cl: 44.0, hco3: 510.0 }
    , { name: "GOCCIA DI CARNIA", ca: 17.6, mg: 4.0, na: 1.2, so4: 2.8, cl: 0.3, hco3: 79.0 }
    , { name: "GUIZZA", ca: 46.0, mg: 31.0, na: 7.7, so4: 4.6, cl: 2.8, hco3: 296.0 }
    , { name: "JULIA", ca: 28.5, mg: 7.9, na: 0.25, so4: 1.8, cl: 0.3, hco3: 132.0 }
    , { name: "KAISERWASSER", ca: 180.0, mg: 41.0, na: 1.2, so4: 400.0, cl: 0.6, hco3: 230.0 }
    , { name: "LA FRANCESCA", ca: 56.0, mg: 13.84, na: 80.15, so4: 64.1, cl: 38.0, hco3: 329.0 }
    , { name: "LA VENA D'ORO", ca: 62.5, mg: 8.2, na: 1.15, so4: 5.14, cl: 1.21, hco3: 232.4 }
    , { name: "LA VITTORIA", ca: 864.0, mg: 110.0, na: 119.6, so4: 1386.0, cl: 102.8, hco3: 1614.0 }
    , { name: "L'AQUA", ca: 61.7, mg: 18.0, na: 15.0, so4: 23.9, cl: 10.6, hco3: 266.6 }
    , { name: "LAURENTINA", ca: 230.5, mg: 36.5, na: 98.0, so4: 124.4, cl: 35.5, hco3: 1140.7 }
    , { name: "LAURETANA", ca: 1.0, mg: 0.3, na: 1.1, so4: 1.4, cl: 0.42, hco3: 3.6 }
    , { name: "LAVAREDO", ca: 310.0, mg: 72.0, na: 2.4, so4: 840.0, cl: 1.0, hco3: 234.0 }
    , { name: "LENTULA", ca: 63.9, mg: 15.9, na: 4.1, so4: 39.0, cl: 4.5, hco3: 232.0 }
    , { name: "LETE", ca: 321.0, mg: 17.5, na: 5.1, so4: 8.65, cl: 7.64, hco3: 1055.3 }
    , { name: "LEVIA", ca: 18.3, mg: 15.7, na: 44.0, so4: -1, cl: 68.0, hco3: 71.1 }
    , { name: "LEVICO CASARA", ca: 7.0, mg: 1.2, na: 1.0, so4: 10.0, cl: 0.54, hco3: 17.0 }
    , { name: "LEVISSIMA", ca: 21.0, mg: 1.7, na: 1.9, so4: 16.9, cl: -1, hco3: 57.1 }
    , { name: "LIEVE", ca: 65.0, mg: 17.0, na: 15.3, so4: 51.0, cl: 10.3, hco3: 257.0 }
    , { name: "LILIA", ca: 33.9, mg: 10.5, na: -1, so4: -1, cl: -1, hco3: 268.0 }
    , { name: "LIMPAS", ca: -1, mg: -1, na: 36.0, so4: -1, cl: -1, hco3: -1 }
    , { name: "LIMPIA", ca: 48.0, mg: 23.3, na: 0.42, so4: 9.4, cl: 0.78, hco3: 244.0 }
    , { name: "LIMPIDA", ca: 49.6, mg: 16.6, na: 18.2, so4: 24.0, cl: 23.2, hco3: 222.7 }
    , { name: "RECOARO", ca: 35.2, mg: 14.0, na: 0.9, so4: 19.9, cl: 0.9, hco3: 150.0 }
    , { name: "LUCE", ca: 43.6, mg: 14.6, na: 58.5, so4: -1, cl: 61.0, hco3: 217.0 }
    , { name: "LUNA", ca: 47.6, mg: 17.2, na: 3.5, so4: 54.8, cl: 4.6, hco3: 156.2 }
    , { name: "LURISIA", ca: -1, mg: 0.35, na: 2.7, so4: -1, cl: -1, hco3: 18.0 }
    , { name: "LYDE", ca: 118.9, mg: 51.3, na: 30.0, so4: 35.4, cl: 49.9, hco3: 591.8 }
    , { name: "LYNX", ca: 53.4, mg: 4.6, na: 2.7, so4: 9.4, cl: 4.0, hco3: 180.0 }
    , { name: "MADONNA DELLA  MERCEDE", ca: 104.7, mg: 44.0, na: 30.0, so4: 71.8, cl: 30.6, hco3: 486.0 }
    , { name: "MADONNA DELLA GUARDIA", ca: 20.5, mg: 4.3, na: 6.5, so4: 14.9, cl: 10.3, hco3: 72.7 }
    , { name: "MADONNA DELL'AMBRO", ca: 82.96, mg: 33.6, na: 12.2, so4: 30.09, cl: 12.5, hco3: 396.62 }
    , { name: "MANGIATORELLA", ca: 6.2, mg: 1.6, na: 9.8, so4: 4.7, cl: 11.8, hco3: 28.9 }
    , { name: "MANIVA", ca: 38.0, mg: 4.3, na: 2.0, so4: 5.4, cl: 2.0, hco3: 131.8 }
    , { name: "MARZIA", ca: 302.8, mg: 87.4, na: 10.5, so4: 540.0, cl: 17.2, hco3: 610.0 }
    , { name: "MAXIM'S", ca: 21.5, mg: 1.5, na: 4.2, so4: 6.62, cl: 6.65, hco3: 57.0 }
    , { name: "MEO", ca: 23.5, mg: 7.71, na: 11.92, so4: 5.0, cl: 11.8, hco3: 119.0 }
    , { name: "MERANER", ca: 4.3, mg: 1.3, na: 4.0, so4: -1, cl: -1, hco3: 16.0 }
    , { name: "MIA", ca: 95.2, mg: 8.7, na: 9.2, so4: 10.5, cl: 8.4, hco3: 333.0 }
    , { name: "MISIA", ca: 72.2, mg: 3.4, na: 3.22, so4: 20.34, cl: 6.88, hco3: 209.0 }
    , { name: "MOLINO DELLE OGNE", ca: 53.0, mg: 1.9, na: 5.5, so4: 7.1, cl: 11.0, hco3: 164.7 }
    , { name: "MOLISIA", ca: 74.0, mg: 7.0, na: 5.0, so4: 5.26, cl: 4.46, hco3: 252.0 }
    , { name: "MONTE BIANCO", ca: 30.0, mg: 25.0, na: 15.0, so4: 375.0, cl: 1.0, hco3: 62.0 }
    , { name: "MONTE CIMONE", ca: 33.0, mg: 5.5, na: 2.7, so4: 9.9, cl: 2.8, hco3: 125.0 }
    , { name: "MONTEFORTE", ca: 96.5, mg: 15.2, na: 3.6, so4: 21.2, cl: 4.7, hco3: 351.0 }
    , { name: "MONTEVERDE", ca: 28.0, mg: 8.65, na: 16.8, so4: 25.4, cl: 7.4, hco3: 134.5 }
    , { name: "MONTICELLO", ca: 48.0, mg: 20.6, na: 48.1, so4: 19.9, cl: 29.6, hco3: 384.3 }
    , { name: "MONTINVERNO", ca: 132.8, mg: 38.9, na: 17.5, so4: 183.5, cl: 7.1, hco3: 401.0 }
    , { name: "MONVISO", ca: -1, mg: 0.61, na: 1.8, so4: 3.7, cl: 0.5, hco3: -1 }
    , { name: "MOSCHETTA", ca: 13.6, mg: 8.8, na: 11.7, so4: 8.4, cl: 15.0, hco3: 97.6 }
    , { name: "MOTETTE", ca: 55.0, mg: 1.4, na: 5.7, so4: 8.3, cl: 10.0, hco3: 163.0 }
    , { name: "NATIA", ca: 35.0, mg: 4.0, na: 32.0, so4: 3.0, cl: 16.0, hco3: 213.0 }
    , { name: "NEREA", ca: 55.0, mg: 0.6, na: 1.7, so4: 2.9, cl: 4.3, hco3: 171.0 }
    , { name: "NEVE", ca: 34.4, mg: 5.5, na: 3.5, so4: 1.3, cl: 1.2, hco3: 136.7 }
    , { name: "NINFA LEGGERA", ca: 40.0, mg: -1, na: -1, so4: 13.0, cl: 24.0, hco3: 280.0 }
    , { name: "NOVA SORGENTE CESA", ca: 39.0, mg: 4.2, na: 3.1, so4: 11.3, cl: 6.2, hco3: 119.0 }
    , { name: "NUOVA ACQUACHIARA", ca: 30.0, mg: 16.2, na: 1.5, so4: 10.1, cl: 1.6, hco3: 159.0 }
    , { name: "NUOVA GAREISA", ca: 30.4, mg: 2.8, na: 11.6, so4: 19.9, cl: -1, hco3: -1 }
    , { name: "ORIANNA", ca: 103.0, mg: 32.0, na: 33.0, so4: 65.0, cl: 52.0, hco3: 412.0 }
    , { name: "OROBICA", ca: 67.7, mg: 18.7, na: 18.3, so4: 28.7, cl: 9.8, hco3: 292.0 }
    , { name: "ORSINELLA", ca: 55.1, mg: 20.5, na: 12.4, so4: 6.4, cl: 14.6, hco3: 251.0 }
    , { name: "PALINA", ca: 43.0, mg: 6.0, na: 7.7, so4: 18.0, cl: 9.4, hco3: 142.0 }
    , { name: "PALMENSE DEL PICENO", ca: 12080.0, mg: 2131.0, na: 3392.0, so4: 3721.0, cl: 8884.0, hco3: 33600.0 }
    , { name: "PANNA", ca: 32.9, mg: 6.5, na: 6.4, so4: 21.0, cl: 9.0, hco3: 106.0 }
    , { name: "PARAVITA", ca: 106.7, mg: 38.7, na: 70.2, so4: 42.2, cl: 120.0, hco3: 446.0 }
    , { name: "PASUBIO (ALISEA FONTE PASUBIO)", ca: 57.5, mg: 28.8, na: 1.3, so4: 50.3, cl: 1.6, hco3: 259.0 }
    , { name: "PEJO FONTE ALPINA", ca: 19.0, mg: 4.9, na: 2.2, so4: 27.2, cl: 0.5, hco3: 54.0 }
    , { name: "PERLA", ca: 70.0, mg: 33.0, na: 112.0, so4: 64.0, cl: 120.0, hco3: 395.0 }
    , { name: "PETRA PERTUSA", ca: 88.0, mg: 2.5, na: 12.0, so4: 12.0, cl: 20.0, hco3: 253.0 }
    , { name: "PIAN DELLA MUSSA", ca: 5.5, mg: 2.8, na: 0.8, so4: 5.6, cl: 0.2, hco3: 23.0 }
    , { name: "PIC", ca: 42.0, mg: 13.2, na: 2.9, so4: 18.0, cl: 1.5, hco3: -1 }
    , { name: "PIERSANTI", ca: 457.0, mg: 117.8, na: 210.0, so4: 379.0, cl: 283.0, hco3: 1598.0 }
    , { name: "PIEVE", ca: 26.9, mg: 15.8, na: 26.7, so4: 16.2, cl: 46.1, hco3: 112.8 }
    , { name: "PINETA SORGENTE SALES", ca: 58.0, mg: 9.5, na: 0.4, so4: 6.1, cl: 0.8, hco3: 218.0 }
    , { name: "PIODA", ca: 33.7, mg: 14.2, na: 1.6, so4: 10.4, cl: -1, hco3: 162.0 }
    , { name: "PLOSE", ca: 2.6, mg: 1.6, na: 1.1, so4: 3.1, cl: 0.5, hco3: 13.0 }
    , { name: "POZZILLO", ca: 64.5, mg: 74.0, na: -1, so4: 255.0, cl: 198.5, hco3: -1 }
    , { name: "PRACASTELLO", ca: 168.0, mg: 48.6, na: 29.4, so4: 408.4, cl: 50.4, hco3: 241.0 }
    , { name: "PRADIS", ca: 36.4, mg: 17.0, na: 0.6, so4: -1, cl: 1.2, hco3: -1 }
    , { name: "PRATA", ca: 150.0, mg: 11.5, na: 4.0, so4: -1, cl: 6.2, hco3: 460.0 }
    , { name: "PREALPI", ca: 83.5, mg: 21.9, na: 20.4, so4: 57.6, cl: 14.5, hco3: 315.0 }
    , { name: "PRESOLANA", ca: 51.0, mg: 197.0, na: 5.0, so4: 73.0, cl: 0.9, hco3: 240.0 }
    , { name: "PRIMULA", ca: 88.5, mg: 34.4, na: 8.8, so4: 84.3, cl: 4.4, hco3: 356.8 }
    , { name: "PURA", ca: 21.9, mg: 14.1, na: 47.7, so4: -1, cl: 59.0, hco3: 134.1 }
    , { name: "QUARZIA", ca: 72.78, mg: 9.45, na: 1.5, so4: 8.31, cl: 3.4, hco3: 244.0 }
    , { name: "RADIOSA", ca: 87.0, mg: 15.0, na: 8.0, so4: 20.0, cl: 6.0, hco3: 308.0 }
    , { name: "REALE DI TARSOGNO (ALISEA)", ca: 13.0, mg: 2.2, na: 2.9, so4: 7.4, cl: 4.3, hco3: 45.0 }
    , { name: "REGILLA", ca: 24.02, mg: 9.23, na: 7.5, so4: -1, cl: -1, hco3: -1 }
    , { name: "REGINA", ca: 657.3, mg: 119.16, na: 5570.0, so4: 1506.0, cl: 8792.0, hco3: 619.15 }
    , { name: "RIVIVO", ca: 13.2, mg: 119.0, na: 10.9, so4: 38.7, cl: 21.2, hco3: 581.0 }
    , { name: "ROANA", ca: 38.0, mg: -1, na: 0.8, so4: 0.9, cl: 1.8, hco3: 112.2 }
    , { name: "ROCCABIANCA", ca: 22.9, mg: 10.6, na: 14.5, so4: 19.6, cl: 18.8, hco3: 112.9 }
    , { name: "ROCCA GALGANA", ca: 17.43, mg: 39.52, na: 7.1, so4: 60.98, cl: 5.03, hco3: 176.29 }
    , { name: "ROCCE SARDE", ca: 15.0, mg: 8.6, na: 108.0, so4: 23.0, cl: -1, hco3: 154.07 }
    , { name: "ROCCHETTA", ca: 57.12, mg: 3.48, na: 4.66, so4: 8.27, cl: 7.82, hco3: 177.8 }
    , { name: "ROCCOLO", ca: 22.0, mg: 2.06, na: 1.16, so4: 2.1, cl: 1.44, hco3: 75.5 }
    , { name: "RUGIADA", ca: 65.0, mg: 17.0, na: 15.3, so4: 51.0, cl: 103.0, hco3: 257.0 }
    , { name: "RUSCELLA", ca: 81.8, mg: 11.7, na: 17.3, so4: 14.2, cl: 35.5, hco3: 300.0 }
    , { name: "S. ANDREA", ca: 59.8, mg: 56.0, na: 73.0, so4: 139.0, cl: 17.2, hco3: 457.5 }
    , { name: "S. ANGELO", ca: 23.3, mg: 16.2, na: 46.9, so4: -1, cl: 80.0, hco3: 116.0 }
    , { name: "S. ANTONIO", ca: 33.4, mg: 5.6, na: 3.9, so4: 1.8, cl: 1.3, hco3: 134.0 }
    , { name: "S. BERNARDO", ca: 15.3, mg: 2.15, na: 1.8, so4: 5.3, cl: 3.2, hco3: 50.1 }
    , { name: "S. BERNARDO SORGENTE DELLA ROCCA", ca: 46.0, mg: 0.4, na: 0.7, so4: 3.1, cl: 0.6, hco3: 140.0 }
    , { name: "S. BERNARDO SORGENTE ROCCIAVIVA", ca: 9.5, mg: 0.6, na: 0.6, so4: 2.3, cl: 0.7, hco3: 30.2 }
    , { name: "S. CARLO", ca: 2.9, mg: 2.4, na: 9.0, so4: 2.5, cl: 13.5, hco3: 11.0 }
    , { name: "S. CASSIANO", ca: 86.0, mg: 1.4, na: 6.6, so4: 8.1, cl: 11.2, hco3: 255.0 }
    , { name: "S. FRANCESCO", ca: 34.1, mg: 5.6, na: 3.8, so4: 1.4, cl: 1.8, hco3: 131.0 }
    , { name: "S. GIACOMO", ca: 144.02, mg: 13.84, na: 24.97, so4: 35.74, cl: 29.16, hco3: 474.66 }
    , { name: "S. GIORGIO", ca: 27.0, mg: 15.0, na: 42.6, so4: -1, cl: 72.5, hco3: 113.4 }
    , { name: "S. LUCIA", ca: 105.0, mg: 66.0, na: 250.0, so4: -1, cl: 70.9, hco3: 1159.32 }
    , { name: "S. LUIGI", ca: 61.3, mg: 13.8, na: 0.9, so4: 14.0, cl: 1.9, hco3: 234.9 }
    , { name: "S. MARIA DEGLI ANGELI", ca: 20.8, mg: 7.3, na: 22.1, so4: 8.4, cl: 12.6, hco3: 140.0 }
    , { name: "S. PELLEGRINO", ca: 164.0, mg: 49.5, na: 31.2, so4: 402.0, cl: 49.4, hco3: 243.0 }
    , { name: "S. PIETRO", ca: 191.0, mg: 55.84, na: 88.0, so4: 45.0, cl: 28.36, hco3: 1073.6 }
    , { name: "S. SILVESTRO", ca: 288.0, mg: 80.2, na: 2.4, so4: 820.5, cl: 1.4, hco3: 231.8 }
    , { name: "SACRAMORA", ca: 117.0, mg: 23.25, na: 47.5, so4: 114.0, cl: 61.0, hco3: 340.0 }
    , { name: "SAN BENEDETTO", ca: 48.6, mg: 29.4, na: 5.8, so4: 4.1, cl: 2.4, hco3: 301.0 }
    , { name: "SANCARLO SPINONE", ca: 107.2, mg: 27.2, na: 6.3, so4: 67.0, cl: 4.3, hco3: 378.3 }
    , { name: "SAN CIRO", ca: 55.25, mg: 88.86, na: 120.0, so4: -1, cl: -1, hco3: 744.2 }
    , { name: "SAN DANIELE", ca: 59.2, mg: 7.2, na: 7.7, so4: 29.2, cl: 4.7, hco3: 211.8 }
    , { name: "SAN DONATO", ca: 66.4, mg: 8.2, na: 99.3, so4: 252.0, cl: 42.5, hco3: 195.2 }
    , { name: "SANFAUSTINO", ca: 422.8, mg: 16.5, na: 20.75, so4: 96.7, cl: 18.6, hco3: 1281.0 }
    , { name: "SAN FELICE", ca: 58.3, mg: 9.1, na: 8.9, so4: 17.1, cl: 9.9, hco3: 194.0 }
    , { name: "SANGIULIANO", ca: 119.0, mg: 25.0, na: 49.0, so4: 131.0, cl: 52.0, hco3: 366.0 }
    , { name: "SAN GRATO", ca: 13.8, mg: 14.9, na: 2.8, so4: 6.8, cl: 3.6, hco3: 101.0 }
    , { name: "SAN LORENZO", ca: 155.9, mg: 320.2, na: 145.8, so4: 359.0, cl: 35.4, hco3: 1988.0 }
    , { name: "SAN LUCA", ca: 69.76, mg: 13.88, na: 2.87, so4: 5.7, cl: 1.8, hco3: 256.2 }
    , { name: "SAN MARCO", ca: 312.0, mg: 90.6, na: 20.0, so4: 4.0, cl: 116.9, hco3: 1605.0 }
    , { name: "SAN MARINO", ca: 110.0, mg: 8.0, na: 8.2, so4: 14.0, cl: 11.0, hco3: 360.0 }
    , { name: "SAN MARTINO", ca: 167.0, mg: 50.0, na: 750.0, so4: 281.2, cl: 290.69, hco3: 2288.0 }
    , { name: "SAN MICHELE", ca: 5.6, mg: 0.7, na: 2.1, so4: 4.0, cl: 0.4, hco3: -1 }
    , { name: "SAN MODERANNO", ca: 55.2, mg: 30.72, na: 37.94, so4: 63.25, cl: 10.64, hco3: 317.2 }
    , { name: "SAN PANCRAZIO", ca: 54.4, mg: 42.3, na: 584.0, so4: 122.0, cl: 699.0, hco3: 280.6 }
    , { name: "SAN PANTALEO", ca: 14.6, mg: -1, na: 78.0, so4: -1, cl: -1, hco3: 115.17 }
    , { name: "SAN PAOLO", ca: 298.0, mg: 76.0, na: 248.0, so4: 312.0, cl: 51.0, hco3: 1921.5 }
    , { name: "SAN ROCCO", ca: 77.0, mg: 42.0, na: 6.3, so4: 25.5, cl: 55.2, hco3: 392.8 }
    , { name: "SANCT ZACHARIAS", ca: 107.0, mg: 11.5, na: 8.5, so4: 109.0, cl: 10.0, hco3: 250.0 }
    , { name: "SANDALIA", ca: 35.7, mg: 9.24, na: 520.0, so4: 59.9, cl: 329.0, hco3: 927.0 }
    , { name: "SANGEMINI", ca: 325.1, mg: 15.23, na: 19.6, so4: 55.2, cl: 16.3, hco3: 1021.0 }
    , { name: "SANTA CLARA", ca: 42.5, mg: 4.65, na: 4.1, so4: 16.1, cl: -1, hco3: 120.0 }
    , { name: "SANTA CROCE SPONGA", ca: 48.1, mg: 4.57, na: 1.23, so4: 1.3, cl: -1, hco3: 231.8 }
    , { name: "SANTA MARIA", ca: 87.3, mg: 5.1, na: 18.0, so4: 7.0, cl: 29.1, hco3: 262.0 }
    , { name: "SANTA REPARATA", ca: 131.0, mg: 31.0, na: 11.0, so4: -1, cl: -1, hco3: -1 }
    , { name: "SANTA RITA", ca: 15.0, mg: 2.9, na: 4.5, so4: 4.5, cl: 5.4, hco3: 50.5 }
    , { name: "SANTA VITTORIA", ca: 52.6, mg: 8.2, na: 1.6, so4: 22.8, cl: 0.79, hco3: 175.0 }
    , { name: "SANTAFIORA", ca: 66.2, mg: 32.6, na: 110.5, so4: 66.7, cl: 127.1, hco3: 386.7 }
    , { name: "SANTAGATA", ca: 280.0, mg: 20.0, na: 49.0, so4: 5.0, cl: 20.0, hco3: 1140.0 }
    , { name: "SANT'ANTONIO SPONGA", ca: 50.0, mg: 5.0, na: 1.6, so4: 1.3, cl: 7.0, hco3: 195.0 }
    , { name: "SANT'ELENA", ca: 126.0, mg: 11.9, na: 20.0, so4: 39.2, cl: 26.0, hco3: 362.0 }
    , { name: "SANTO STEFANO", ca: 57.8, mg: 14.0, na: 2.6, so4: 3.4, cl: 8.8, hco3: 251.0 }
    , { name: "SASSOVIVO", ca: 70.0, mg: 1.3, na: 4.2, so4: 5.5, cl: 7.9, hco3: 215.0 }
    , { name: "SATTAI", ca: 21.24, mg: 11.18, na: 56.0, so4: 23.06, cl: 88.24, hco3: 75.0 }
    , { name: "SEPINIA", ca: 74.7, mg: 4.7, na: 4.0, so4: 3.2, cl: 4.5, hco3: 264.0 }
    , { name: "SERRICELLA", ca: 3.6, mg: 4.6, na: 13.0, so4: 4.86, cl: 3.9, hco3: 42.7 }
    , { name: "SIETE FUENTES DI S. LEONARDO", ca: 8.2, mg: 5.3, na: 27.5, so4: 1.9, cl: 42.1, hco3: 40.9 }
    , { name: "SILVANA", ca: 85.9, mg: 27.4, na: 27.2, so4: 92.5, cl: 10.6, hco3: 341.6 }
    , { name: "SMERALDINA", ca: 13.1, mg: 7.9, na: 30.5, so4: 10.62, cl: 52.78, hco3: 50.34 }
    , { name: "SOLARIA", ca: 37.2, mg: 14.7, na: -1, so4: -1, cl: -1, hco3: 228.0 }
    , { name: "SOLE", ca: 108.0, mg: 31.1, na: 2.6, so4: 19.3, cl: 2.9, hco3: 439.3 }
    , { name: "SOLFUREA", ca: 599.4, mg: 173.8, na: 160.6, so4: 1754.0, cl: 152.2, hco3: 629.5 }
    , { name: "SORGENTE DEGLI ONTANI", ca: 32.1, mg: 11.3, na: 5.7, so4: 12.9, cl: 8.8, hco3: 143.5 }
    , { name: "SORGENTE DEL CACCIATORE", ca: 64.0, mg: 2.0, na: 2.6, so4: 4.8, cl: -1, hco3: -1 }
    , { name: "SORGENTE DELL'AMORE", ca: 94.0, mg: 13.1, na: 12.1, so4: 22.0, cl: 14.8, hco3: 336.0 }
    , { name: "SORGENTE DEL TIGLIO", ca: 159.5, mg: 52.0, na: 172.5, so4: 112.3, cl: 184.4, hco3: 760.3 }
    , { name: "SORGENTE GRIGNA", ca: 51.7, mg: 15.7, na: 1.6, so4: 8.6, cl: 2.6, hco3: 209.8 }
    , { name: "SORGENTE LINDA", ca: 89.1, mg: 26.5, na: 13.9, so4: 35.9, cl: 14.3, hco3: 361.0 }
    , { name: "SORGENTE LISSA", ca: 36.2, mg: 18.4, na: 0.6, so4: 14.4, cl: -1, hco3: 189.0 }
    , { name: "SORGENTE PERGOLI", ca: 688.0, mg: 42.7, na: 44.1, so4: 1440.0, cl: 63.0, hco3: 576.0 }
    , { name: "SORGENTE SERRA POLICARETTO DELLA SILA", ca: 16.26, mg: 9.54, na: 14.81, so4: 6.54, cl: 9.44, hco3: 108.3 }
    , { name: "SORGENTE TESORINO", ca: 126.8, mg: 27.9, na: 36.8, so4: 159.4, cl: 36.6, hco3: 359.9 }
    , { name: "SOVRANA", ca: 74.6, mg: 39.1, na: 6.3, so4: 14.0, cl: -1, hco3: 406.3 }
    , { name: "SPAREA", ca: -1, mg: 0.63, na: 1.2, so4: 4.1, cl: 0.4, hco3: -1 }
    , { name: "STELLA ALPINA", ca: 10.0, mg: 2.7, na: 0.97, so4: 4.6, cl: -1, hco3: 36.6 }
    , { name: "SUIO", ca: 132.13, mg: 45.16, na: 4.0, so4: 42.8, cl: -1, hco3: 573.4 }
    , { name: "SURGIVA", ca: 6.9, mg: 0.5, na: 1.5, so4: 2.9, cl: 0.3, hco3: 19.9 }
    , { name: "SVEVA", ca: 241.0, mg: 39.9, na: -1, so4: -1, cl: -1, hco3: 1280.0 }
    , { name: "SYRMA", ca: -1, mg: -1, na: 1.1, so4: 4.8, cl: 0.6, hco3: -1 }
    , { name: "TALIANS", ca: 596.0, mg: 77.0, na: 7.0, so4: 1530.0, cl: 8.0, hco3: 290.0 }
    , { name: "TAMERICI", ca: 802.2, mg: 138.5, na: 6382.0, so4: 1607.0, cl: 10275.0, hco3: 671.8 }
    , { name: "TAVINA", ca: 80.0, mg: 25.3, na: 12.9, so4: 21.2, cl: 8.4, hco3: 366.1 }
    , { name: "TELESE", ca: 387.0, mg: 73.0, na: 135.0, so4: 57.0, cl: 158.0, hco3: 830.0 }
    , { name: "TETTUCCIO", ca: 392.8, mg: 64.44, na: 2622.0, so4: 732.0, cl: 4212.0, hco3: 488.0 }
    , { name: "TINNEA", ca: 67.1, mg: 5.6, na: 3.4, so4: 13.2, cl: 5.4, hco3: 214.0 }
    , { name: "TIONE", ca: 15.2, mg: 5.3, na: 12.5, so4: 7.0, cl: 10.4, hco3: 90.0 }
    , { name: "TOKA", ca: 216.0, mg: 48.4, na: -1, so4: -1, cl: -1, hco3: 1562.0 }
    , { name: "TOLENTINO", ca: 116.18, mg: 29.16, na: 52.45, so4: -1, cl: -1, hco3: 457.5 }
    , { name: "TRAFICANTE", ca: 76.1, mg: 23.7, na: 331.5, so4: -1, cl: -1, hco3: 714.0 }
    , { name: "TRE FONTANE", ca: 61.0, mg: 9.47, na: 7.3, so4: -1, cl: 1.9, hco3: -1 }
    , { name: "TRE SANTI", ca: 141.0, mg: 9.9, na: 13.1, so4: 23.6, cl: 19.8, hco3: 430.0 }
    , { name: "TULLIA", ca: 70.8, mg: 7.65, na: 3.19, so4: 31.8, cl: 6.89, hco3: 233.0 }
    , { name: "ULIVETO", ca: 171.0, mg: 27.8, na: 74.4, so4: 104.0, cl: 78.7, hco3: 574.0 }
    , { name: "ULMETA", ca: 32.3, mg: 5.4, na: 1.0, so4: 3.6, cl: 1.2, hco3: 122.0 }
    , { name: "URESSO", ca: 32.54, mg: 24.1, na: 3.7, so4: 832.8, cl: 1.7, hco3: 75.64 }
    , { name: "VAIA", ca: 24.7, mg: 4.4, na: 3.6, so4: 17.1, cl: 1.2, hco3: 86.0 }
    , { name: "VAL DI LENTRO", ca: 62.0, mg: 4.7, na: 4.8, so4: 10.8, cl: 5.8, hco3: 201.8 }
    , { name: "VAL DI METI", ca: 75.27, mg: 23.3, na: 12.0, so4: 29.2, cl: 17.3, hco3: 329.6 }
    , { name: "VALBINA", ca: 7.0, mg: 2.06, na: 1.6, so4: 2.6, cl: 1.06, hco3: 32.8 }
    , { name: "VALLE REALE POPOLI", ca: 90.24, mg: 16.4, na: 3.2, so4: 17.62, cl: 3.74, hco3: 335.16 }
    , { name: "VALLE STURA", ca: 9.0, mg: 0.7, na: 1.2, so4: 5.7, cl: -1, hco3: 28.0 }
    , { name: "VALLECHIARA", ca: 2.5, mg: 1.7, na: 2.8, so4: 3.1, cl: 5.1, hco3: -1 }
    , { name: "VALLICELLE", ca: 115.0, mg: 27.0, na: 30.0, so4: 22.1, cl: 37.2, hco3: 475.8 }
    , { name: "VALMORA ABURU", ca: 10.0, mg: 2.9, na: 1.4, so4: 6.3, cl: 0.29, hco3: -1 }
    , { name: "VALVERDE", ca: 2.9, mg: 0.5, na: 3.4, so4: 2.2, cl: 2.4, hco3: 9.3 }
    , { name: "VALVIVA", ca: 35.7, mg: 16.1, na: 35.2, so4: -1, cl: 9.2, hco3: 270.7 }
    , { name: "VARANINA", ca: 119.4, mg: 31.0, na: 21.5, so4: 150.0, cl: 14.8, hco3: 365.0 }
    , { name: "VASCIANO", ca: 392.7, mg: 58.0, na: 47.5, so4: 170.1, cl: 37.2, hco3: 1354.2 }
    , { name: "VENA D ORO", ca: 62.0, mg: 8.0, na: 1.3, so4: 6.0, cl: 2.0, hco3: 229.0 }
    , { name: "VENTASSO", ca: 39.2, mg: 4.6, na: 9.2, so4: 24.9, cl: 3.0, hco3: 140.0 }
    , { name: "VERA", ca: 35.9, mg: 12.6, na: 2.0, so4: 19.2, cl: 2.6, hco3: 148.0 }
    , { name: "VERDIANA", ca: 91.3, mg: 24.0, na: 32.0, so4: 49.8, cl: 39.7, hco3: 353.8 }
    , { name: "VERNA", ca: 40.3, mg: 5.4, na: 6.5, so4: 20.0, cl: 7.7, hco3: 134.2 }
    , { name: "VERRUCA", ca: 119.8, mg: 40.1, na: 94.5, so4: 73.6, cl: 140.05, hco3: 480.05 }
    , { name: "VESUVIO", ca: 78.4, mg: 142.9, na: 172.0, so4: 220.2, cl: 369.0, hco3: 952.8 }
    , { name: "VIGEZZO", ca: 3.372, mg: 1.008, na: 1.512, so4: 92.554, cl: 2.52, hco3: 13.42 }
    , { name: "VIS", ca: 155.0, mg: 25.0, na: 50.0, so4: 185.0, cl: 8.9, hco3: -1 }
    , { name: "VISCIOLO", ca: 56.0, mg: 12.11, na: 67.8, so4: 67.6, cl: 26.2, hco3: 302.0 }
    , { name: "VITAS (DANONE VITASNELLA)", ca: 86.0, mg: 26.0, na: 3.0, so4: 83.0, cl: 2.0, hco3: 301.0 }
    , { name: "VITASANA", ca: 52.0, mg: 14.3, na: 12.3, so4: 33.0, cl: 20.1, hco3: 201.3 }
    , { name: "VITOLOGATTI", ca: 440.0, mg: 147.0, na: 22.0, so4: 28.5, cl: 21.0, hco3: 2105.0 }
    , { name: "VIVA", ca: 75.0, mg: 7.8, na: 3.6, so4: 42.0, cl: 6.0, hco3: 213.0 }
    , { name: "VIVIEN", ca: 24.5, mg: 7.9, na: -1, so4: -1, cl: -1, hco3: 190.0 }
    , { name: "VITA MIA", ca: 78.4, mg: 32.0, na: 1.05, so4: 138.0, cl: 1.0, hco3: 224.0 }
    , { name: "DONAT", ca: 39000.0, mg: 1.05, na: 1.5, so4: 2.3, cl: 5800.0, hco3: 7.2 }
    , { name: "VALPURA", ca: 33.49, mg: 5.51, na: 3.55, so4: 1.61, cl: 1.42, hco3: 133.59 }
    , { name: "s. rosalia", ca: 60.5, mg: 19.2, na: 8.0, so4: 15.6, cl: 14.1, hco3: 254.0 }
    , { name: "NEVIA", ca: 6.8, mg: 2.2, na: 11.1, so4: 6.9, cl: 10.7, hco3: 42.7 }
    , { name: "s.daniele - oro blu", ca: 63.0, mg: 9.6, na: 1.4, so4: 32.0, cl: 4.6, hco3: 217.2 }
    , { name: "sorgente del bucaneve", ca: 71.0, mg: 27.0, na: 4.0, so4: 46.0, cl: 6.0, hco3: -1 }
    , { name: "Alta Fonte", ca: 2.8, mg: -1, na: 1.2, so4: -1, cl: -1, hco3: -1 }
    , { name: "SINCERA", ca: 4.0, mg: -1, na: 1.0, so4: -1, cl: -1, hco3: -1 }
    , { name: "HQ", ca: 2.8, mg: -1, na: 1.2, so4: -1, cl: -1, hco3: -1 }
    , { name: "STELLA DEL MONVISO", ca: 2.8, mg: -1, na: 1.2, so4: -1, cl: -1, hco3: -1 }
    , { name: "fabiaviva", ca: 435.0, mg: 5.17, na: 7.08, so4: -1, cl: -1, hco3: 1252.0 }
    , { name: "Milicia", ca: 98.0, mg: -1, na: 57.0, so4: 39.0, cl: -1, hco3: 263.0 }
    , { name: "Paraviso", ca: 68.8, mg: 7.9, na: 1.4, so4: 8.2, cl: 2.8, hco3: 233.0 }
    , { name: "s.leopoldo", ca: 300.6, mg: 87.4, na: 1508.0, so4: 1056.0, cl: 674.8, hco3: 2973.0 }
    , { name: "acqua forte", ca: 316.6, mg: 60.8, na: 75.5, so4: 0.9, cl: 30.14, hco3: 1433.0 }
    , { name: "fonte gioiosa", ca: 35.8, mg: 15.5, na: 3.2, so4: 12.5, cl: 6.2, hco3: 149.4 }
    , { name: "VALMORA FUCINE", ca: 2.1, mg: -1, na: 1.5, so4: 4.0, cl: -1, hco3: 6.0 }
    , { name: "TORRICELLA", ca: 271.0, mg: 213.0, na: 2006.0, so4: 360.0, cl: 389.0, hco3: 410.0 }
    , { name: "FONTEVIVO", ca: 46.0, mg: 10.0, na: 29.0, so4: 36.0, cl: 31.0, hco3: 181.0 }
    , { name: "MONTOSO", ca: -1, mg: -1, na: 1.5, so4: 4.9, cl: 0.26, hco3: -1 }
    , { name: "PLINIA DEL TISONE", ca: 33.3, mg: 1.4, na: 1.5, so4: 6.2, cl: 1.8, hco3: 95.8 }
    , { name: "Fonte Etrusca", ca: 104.2, mg: 94.0, na: 122.0, so4: 44.5, cl: 106.0, hco3: 569.8 }
    , { name: "Antesana", ca: 327.3, mg: 257.6, na: 2419.0, so4: 434.8, cl: 4680.0, hco3: 495.5 }
    , { name: "La Valligiana", ca: 2.3, mg: -1, na: 3.4, so4: -1, cl: -1, hco3: -1 }
    , { name: "Dolomia", ca: 26.4, mg: 14.4, na: 0.17, so4: 2.8, cl: 0.3, hco3: 155.0 }
    , { name: "Fonte vela", ca: 61.3, mg: 6.7, na: 3.3, so4: 13.9, cl: 3.9, hco3: 214.0 }
    , { name: "Sorgente del Bucaneve", ca: 76.8, mg: 27.5, na: 3.7, so4: 47.5, cl: 6.7, hco3: -1 }
    , { name: "Alta Valle", ca: 41.5, mg: 4.3, na: 4.0, so4: 4.8, cl: -1, hco3: 144.0 }
    , { name: "smat russian", ca: 60.0, mg: 13.0, na: -1, so4: 33.0, cl: 1.3, hco3: -1 }
    , { name: "smat us", ca: 23.9, mg: 3.2, na: -1, so4: 8.2, cl: -1, hco3: -1 }
    , { name: "Hidria", ca: 88.6, mg: 102.4, na: 145.4, so4: 91.1, cl: 75.1, hco3: 915.5 }
    , { name: "Levico forte", ca: 74.0, mg: 88.0, na: 2.75, so4: 5180.0, cl: 0.65, hco3: -1 }
    , { name: "Primavera delle Alpi", ca: 21.2, mg: 9.5, na: 3.7, so4: 8.9, cl: 3.6, hco3: 95.0 }
    , { name: "Primavera Fonte Delicata", ca: 77.9, mg: 28.4, na: 3.6, so4: 46.7, cl: 8.6, hco3: 305.0 }
    , { name: "Fonte del Principe", ca: 3.6, mg: 1.3, na: 11.9, so4: 3.7, cl: 8.6, hco3: 30.6 }
    , { name: "Eva", ca: 10.2, mg: 4.0, na: 0.28, so4: 1.7, cl: 0.18, hco3: 48.8 }
    , { name: "acqua neve", ca: 329.0, mg: 55.0, na: 42.0, so4: 21.0, cl: 13.0, hco3: 131.0 }
    , { name: "Bracca Nuova Fonte", ca: 66.7, mg: 33.2, na: 1.0, so4: 20.1, cl: 1.4, hco3: -1 }
    , { name: "Mugniva", ca: 2.4, mg: 0.48, na: 1.3, so4: -1, cl: 0.61, hco3: 6.2 }
    , { name: "SORBELLO", ca: 2.8, mg: 1.2, na: 6.6, so4: -1, cl: -1, hco3: -1 }
    , { name: "Ofelia", ca: 94.0, mg: 10.0, na: 39.0, so4: -1, cl: 22.0, hco3: 379.0 }
    , { name: "Fonte essenziale", ca: 576.0, mg: 78.0, na: 8.0, so4: 1560.0, cl: -1, hco3: 283.0 }
    , { name: "Sorgesana", ca: 8800.0, mg: 1190.0, na: 350.0, so4: -1, cl: 560.0, hco3: 32500.0 }
    , { name: "Leo", ca: 6.6, mg: 2.2, na: 13.6, so4: 5.6, cl: 9.9, hco3: 42.7 }
    , { name: "Sabrinella", ca: 64.6, mg: 27.6, na: 29.1, so4: -1, cl: 41.8, hco3: 302.0 }
];

interface KWater {
    name: string; ca: number; mg: number; na: number; so4: number; cl: number; hco3: number
}

const clean = (x: number):number => {
    return Math.max(Math.round(x), 0);
}

const sanitize = (x: KWater):KWater => {
    return {
        name: titleCase(x.name),
        ca: clean(x.ca),
        mg: clean(x.mg),
        na: clean(x.na),
        so4: clean(x.so4),
        cl: clean(x.cl),
        hco3: clean(x.hco3)
    }
}

export default KNOWN_WATERS.map(w => sanitize(w));
