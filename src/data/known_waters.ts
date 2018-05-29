const KNOWN_WATERS = [
  { id: 1, name: "ABRAU", ca: 26.0, mg: 12.5, na: 0.8, so4: -1, cl: -1, hco3: 140.0 }
, { id: 2, name: "ACETOSELLA", ca: 370.0, mg: 68.0, na: 90.0, so4: -1, cl: -1, hco3: 1290.0 }
, { id: 3, name: "ACQUA ARVE'", ca: 596.0, mg: 143.0, na: 377.5, so4: 2010.0, cl: 302.0, hco3: 613.0 }
, { id: 4, name: "ACQUA DEL CARDINALE", ca: 39.3, mg: 0.51, na: 0.81, so4: 0.6, cl: 3.7, hco3: 122.0 }
, { id: 5, name: "ACQUA DEL FAIALLO FONTE ARGENTIERA", ca: 0.8, mg: 0.85, na: 2.85, so4: 0.8, cl: 3.1, hco3: 5.3 }
, { id: 6, name: "ACQUA DELLA GROTTA", ca: -1, mg: -1, na: -1, so4: -1, cl: -1, hco3: -1 }
, { id: 7, name: "ACQUA DELLA MADONNA", ca: 367.0, mg: 69.0, na: 82.0, so4: -1, cl: -1, hco3: 1359.0 }
, { id: 9, name: "ACQUA DI NEPI", ca: 82.0, mg: 28.0, na: 27.0, so4: 38.0, cl: 19.0, hco3: 433.0 }
, { id: 10, name: "ACQUA DI TEMPIO", ca: 13.5, mg: 7.6, na: 37.0, so4: 18.0, cl: 54.71, hco3: -1 }
, { id: 11, name: "ACQUA MADONNA DELLE GRAZIE", ca: -1, mg: -1, na: -1, so4: -1, cl: -1, hco3: -1 }
, { id: 12, name: "ACQUA MAGNA", ca: 50.5, mg: 7.54, na: 20.7, so4: 10.7, cl: 30.13, hco3: 167.75 }
, { id: 13, name: "ACQUA MELLIN", ca: 5.0, mg: 1.2, na: 0.9, so4: -1, cl: 0.4, hco3: -1 }
, { id: 14, name: "ACQUA PLINIANA", ca: 242.0, mg: 56.2, na: 18.0, so4: 676.7, cl: 7.0, hco3: 266.5 }
, { id: 15, name: "ACQUA SACRA", ca: 176.2, mg: 19.9, na: 56.0, so4: 66.5, cl: 42.5, hco3: 689.3 }
, { id: 16, name: "ACQUA SANTA DI CHIANCIANO", ca: 715.0, mg: 173.0, na: 25.5, so4: 1840.0, cl: 25.2, hco3: 842.0 }
, { id: 17, name: "ACQUA SILVA", ca: -1, mg: -1, na: 31.0, so4: 32.5, cl: 4.2, hco3: 119.0 }
, { id: 18, name: "ACQUA TERZIANA", ca: -1, mg: -1, na: -1, so4: -1, cl: -1, hco3: -1 }
, { id: 19, name: "ACQUABAIDA", ca: 99.8, mg: 33.8, na: 9.0, so4: 19.3, cl: 24.9, hco3: 183.0 }
, { id: 20, name: "ACQUAROSSA", ca: 120.2, mg: 150.9, na: 158.5, so4: -1, cl: 70.8, hco3: 1108.4 }
, { id: 21, name: "ACQUAVIVA", ca: -1, mg: -1, na: -1, so4: -1, cl: -1, hco3: -1 }
, { id: 22, name: "ACQUAVIVA DELLE FONTI", ca: -1, mg: -1, na: -1, so4: -1, cl: -1, hco3: -1 }
, { id: 23, name: "ACQUEVIVE", ca: -1, mg: -1, na: -1, so4: -1, cl: -1, hco3: -1 }
, { id: 24, name: "AEMILIA", ca: 76.1, mg: 71.4, na: 48.3, so4: 102.6, cl: 23.7, hco3: 542.7 }
, { id: 25, name: "AGABUNA", ca: -1, mg: -1, na: -1, so4: -1, cl: -1, hco3: -1 }
, { id: 26, name: "AGNANO", ca: 384.7, mg: 75.2, na: 306.0, so4: 362.0, cl: 319.0, hco3: 1391.2 }
, { id: 27, name: "ALBA", ca: 10.0, mg: 1.8, na: 1.7, so4: 6.4, cl: 1.0, hco3: 29.0 }
, { id: 28, name: "ALEXANDER", ca: -1, mg: -1, na: -1, so4: -1, cl: -1, hco3: -1 }
, { id: 29, name: "ALISEA LEONARDO PRIMALUNA", ca: 13.2, mg: 2.5, na: 2.2, so4: 8.1, cl: 0.7, hco3: 42.7 }
, { id: 30, name: "ALLODOLA", ca: 390.7, mg: 145.9, na: 108.8, so4: 76.9, cl: 137.0, hco3: 1935.9 }
, { id: 31, name: "ALPI BIANCHE", ca: 12.5, mg: 0.5, na: 0.9, so4: 8.9, cl: -1, hco3: -1 }
, { id: 32, name: "ALPI COZIE", ca: -1, mg: 0.83, na: 2.1, so4: 4.0, cl: 1.6, hco3: -1 }
, { id: 33, name: "ALPIA", ca: 5.7, mg: 3.4, na: 3.5, so4: 4.7, cl: 1.1, hco3: 33.7 }
, { id: 34, name: "ALTE VETTE", ca: 11.8, mg: 0.6, na: 0.9, so4: 8.9, cl: -1, hco3: 29.5 }
, { id: 35, name: "AMATA", ca: 94.46, mg: 44.96, na: 21.42, so4: 6.13, cl: 31.66, hco3: 485.0 }
, { id: 36, name: "AMBRA", ca: 92.8, mg: 51.84, na: 48.6, so4: -1, cl: 67.35, hco3: 454.45 }
, { id: 37, name: "AMBRIA", ca: -1, mg: -1, na: -1, so4: -1, cl: -1, hco3: -1 }
, { id: 38, name: "AMERINO", ca: 157.0, mg: 8.4, na: 11.5, so4: -1, cl: 17.8, hco3: 429.0 }
, { id: 39, name: "AMICA", ca: -1, mg: -1, na: -1, so4: -1, cl: -1, hco3: -1 }
, { id: 40, name: "AMOROSA", ca: 0.97, mg: 0.6, na: 4.03, so4: 0.85, cl: 6.73, hco3: 4.3 }
, { id: 41, name: "ANGELICA", ca: 91.7, mg: 1.17, na: 3.06, so4: 1.84, cl: 5.62, hco3: 278.0 }
, { id: 42, name: "ANTICA FONTE RABBI", ca: 123.4, mg: 44.9, na: 510.0, so4: 6.8, cl: 232.0, hco3: 1670.0 }
, { id: 43, name: "ANTICA FONTE TARTAVALLE", ca: 521.4, mg: 99.1, na: 12.5, so4: 1516.0, cl: 106.0, hco3: 262.0 }
, { id: 44, name: "APPIA", ca: 140.14, mg: 28.65, na: 50.0, so4: 34.85, cl: 33.68, hco3: 646.6 }
, { id: 45, name: "AQUALIEVE", ca: 5.2, mg: 2.8, na: -1, so4: -1, cl: -1, hco3: -1 }
, { id: 46, name: "AQUA PARMALAT", ca: 19.0, mg: 2.0, na: 1.9, so4: -1, cl: -1, hco3: 20.0 }
, { id: 47, name: "ARVENIS", ca: -1, mg: -1, na: -1, so4: -1, cl: -1, hco3: -1 }
, { id: 48, name: "ATIVA", ca: -1, mg: -1, na: -1, so4: -1, cl: -1, hco3: -1 }
, { id: 49, name: "ATTIVA", ca: 248.0, mg: 578.0, na: 13950.0, so4: 31020.0, cl: 50.3, hco3: 1015.0 }
, { id: 50, name: "AUGUSTA", ca: -1, mg: 43.0, na: 52.0, so4: 45.0, cl: -1, hco3: 390.0 }
, { id: 51, name: "AUREA", ca: -1, mg: -1, na: -1, so4: -1, cl: -1, hco3: -1 }
, { id: 52, name: "AURORA", ca: 632.0, mg: 4.2, na: 5.4, so4: 1380.0, cl: 6.3, hco3: 423.0 }
, { id: 53, name: "AUSONIA", ca: 50.0, mg: 54.8, na: 23.2, so4: 70.0, cl: 10.4, hco3: 403.0 }
, { id: 54, name: "AZZURRA", ca: 78.4, mg: 32.0, na: 1.05, so4: 138.0, cl: 1.0, hco3: 224.0 }
, { id: 55, name: "BALDA", ca: 26.7, mg: 5.1, na: 3.6, so4: 17.0, cl: 2.0, hco3: 90.3 }
, { id: 56, name: "BEBER-SORGENTE DOPPIO", ca: 28.0, mg: 16.0, na: 0.5, so4: 5.0, cl: 0.9, hco3: 162.0 }
, { id: 57, name: "BERNINA", ca: 8.0, mg: 0.75, na: 0.93, so4: 6.5, cl: 0.2, hco3: 19.5 }
, { id: 58, name: "BIA", ca: -1, mg: -1, na: -1, so4: -1, cl: -1, hco3: -1 }
, { id: 59, name: "BOARIO", ca: 131.0, mg: 40.0, na: 5.0, so4: 240.0, cl: 4.0, hco3: 303.0 }
, { id: 60, name: "BORROMEA", ca: 41.0, mg: 6.6, na: 6.15, so4: 94.55, cl: 6.6, hco3: 58.3 }
, { id: 61, name: "BRACCA ANTICA FONTE", ca: 123.8, mg: 44.6, na: 20.0, so4: 265.0, cl: 29.5, hco3: 265.0 }
, { id: 62, name: "CALABRIA", ca: 10.8, mg: 4.3, na: 10.3, so4: 8.7, cl: 15.2, hco3: 45.7 }
, { id: 63, name: "FONTI BAUDA", ca: 4.7, mg: 1.0, na: 3.8, so4: 4.7, cl: 4.0, hco3: 16.0 }
, { id: 64, name: "CALVAGNA", ca: -1, mg: -1, na: -1, so4: -1, cl: -1, hco3: -1 }
, { id: 65, name: "CAMOREI", ca: 62.1, mg: 6.7, na: 4.9, so4: 15.3, cl: 6.5, hco3: 284.6 }
, { id: 66, name: "CANAY", ca: 1.8, mg: 1.62, na: 1.75, so4: 9.26, cl: 1.31, hco3: 5.43 }
, { id: 67, name: "CANCIULLE", ca: 126.9, mg: 4.8, na: 16.1, so4: 347.0, cl: 30.0, hco3: 359.1 }
, { id: 68, name: "CAPANNELLE", ca: 102.61, mg: 23.36, na: 37.0, so4: 27.3, cl: 35.45, hco3: 469.7 }
, { id: 69, name: "CAPPUCCINO", ca: 16.8, mg: 12.7, na: 351.6, so4: 7.9, cl: 1.2, hco3: 536.3 }
, { id: 70, name: "CASTELLINA", ca: 53.0, mg: 2.8, na: 2.2, so4: 2.2, cl: 3.5, hco3: 179.0 }
, { id: 71, name: "CASTELLO", ca: 64.2, mg: 32.7, na: 0.8, so4: 8.0, cl: 1.1, hco3: 349.0 }
, { id: 72, name: "CAVAGRANDE", ca: 10.8, mg: 8.5, na: 36.3, so4: 33.0, cl: 27.6, hco3: -1 }
, { id: 73, name: "CECILIANA", ca: 26.43, mg: 11.17, na: 6.6, so4: 3.0, cl: 21.27, hco3: 122.0 }
, { id: 74, name: "CERELIA", ca: 121.0, mg: -1, na: 5.9, so4: 7.6, cl: 5.6, hco3: 418.0 }
, { id: 75, name: "CERTOSA FONTE CAMARDA", ca: 6.2, mg: 4.12, na: 7.8, so4: 8.4, cl: 14.89, hco3: -1 }
, { id: 76, name: "CERTOSA FONTE PERNA", ca: 8.0, mg: 5.0, na: 4.0, so4: 7.0, cl: 17.5, hco3: -1 }
, { id: 77, name: "CERTOSA FONTE PIETRE BIANCHE", ca: 7.2, mg: 4.8, na: 7.1, so4: 6.1, cl: 7.8, hco3: -1 }
, { id: 78, name: "CERVA", ca: 23.8, mg: 0.4, na: 33.7, so4: 30.5, cl: 14.0, hco3: 131.4 }
, { id: 79, name: "CHIARELLA", ca: 40.6, mg: 22.1, na: 0.9, so4: 8.6, cl: 0.9, hco3: 217.8 }
, { id: 80, name: "CIAPPAZZI", ca: 118.0, mg: 107.0, na: 448.0, so4: 148.0, cl: 117.0, hco3: 1800.0 }
, { id: 81, name: "CIME BIANCHE", ca: 11.7, mg: 1.2, na: 1.4, so4: 8.1, cl: -1, hco3: -1 }
, { id: 82, name: "CINCIANO", ca: 273.6, mg: 46.8, na: 125.2, so4: 105.6, cl: 70.5, hco3: 1178.0 }
, { id: 83, name: "CINTOIA", ca: 10.5, mg: 6.0, na: 9.4, so4: 1.19, cl: 1.5, hco3: 33.2 }
, { id: 84, name: "CINZIA", ca: 92.0, mg: 8.4, na: 9.5, so4: 13.0, cl: 7.8, hco3: 317.259 }
, { id: 85, name: "CISANO", ca: -1, mg: -1, na: -1, so4: -1, cl: -1, hco3: -1 }
, { id: 86, name: "CLAUDIA", ca: 86.0, mg: 21.0, na: 61.0, so4: 41.0, cl: 51.0, hco3: 484.0 }
, { id: 87, name: "CLOTIA", ca: -1, mg: -1, na: -1, so4: -1, cl: -1, hco3: -1 }
, { id: 88, name: "COL DE'VENTI", ca: -1, mg: -1, na: -1, so4: -1, cl: -1, hco3: -1 }
, { id: 89, name: "COLLALLI", ca: 107.5, mg: 58.3, na: 1376.0, so4: 856.2, cl: 1851.0, hco3: 539.0 }
, { id: 90, name: "COLOMBO-SORGENTE ROCCHE DI VALLETTI", ca: -1, mg: -1, na: -1, so4: -1, cl: -1, hco3: -1 }
, { id: 91, name: "CONIANO", ca: 176.0, mg: 64.0, na: 668.0, so4: 179.0, cl: 348.1, hco3: 1836.6 }
, { id: 92, name: "CORALBA", ca: 54.0, mg: 21.0, na: 0.4, so4: 8.6, cl: 0.8, hco3: 255.0 }
, { id: 93, name: "CORINTHIA", ca: 211.6, mg: 67.8, na: 173.3, so4: 526.9, cl: 318.6, hco3: 261.8 }
, { id: 94, name: "CORONA", ca: 12.1, mg: 13.6, na: 31.8, so4: 7.0, cl: 33.7, hco3: 125.0 }
, { id: 95, name: "COTTORELLA", ca: 103.96, mg: 2.84, na: 4.99, so4: 7.09, cl: 12.55, hco3: 313.7 }
, { id: 96, name: "COURMAYEUR", ca: 579.0, mg: 59.0, na: 0.7, so4: 1447.0, cl: 0.4, hco3: 180.0 }
, { id: 97, name: "CRISTALIA'", ca: 44.0, mg: 3.5, na: 1.5, so4: 60.8, cl: -1, hco3: 78.4 }
, { id: 98, name: "CRISTALLO", ca: 3.9, mg: 4.2, na: 11.8, so4: 1.8, cl: 17.0, hco3: 18.3 }
, { id: 99, name: "CRODO LISIEL", ca: 56.4, mg: 6.5, na: 5.4, so4: 84.5, cl: 2.1, hco3: 109.0 }
, { id: 100, name: "CUORE DI TOSCANA", ca: -1, mg: -1, na: -1, so4: -1, cl: -1, hco3: -1 }
, { id: 101, name: "CUTOLO RIONERO", ca: 50.0, mg: 13.1, na: 72.4, so4: 54.0, cl: 34.0, hco3: 298.0 }
];

export default KNOWN_WATERS;