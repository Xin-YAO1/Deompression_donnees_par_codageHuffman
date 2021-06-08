Deompression_donnees_par_codageHuffman

Le codage de Huffman, du nom de son concepteur, est une méthode statistique de compression de
données. Son principe est de remplacer un caractère (ou symbole) par une suite de bits de longueur
variable. L'idée sous-jacente est de coder ce qui est fréquent sur peu de bits et au contraire ce qui est
rare sur des séquences de bits plus longues. Le codage de Huffman permet une compression sans
perte, c’est-à-dire qu’une suite de bits strictement identique à l’originale est restituée par
décompression. Il nécessite cependant que soit connues (ou estimées) les fréquences d’apparition des
différents symboles à coder. Il existe ainsi plusieurs variantes de l’algorithme de Huffman (statique,
semi-adaptatif ou adaptatif) aujourd’hui utilisées dans des algorithmes de compression de fichiers tels
que gzip.

Ce sujet concerne la phase de décompression de l’algorithme dans laquelle un texte compressé pourra
être décodé si l’alphabet d’origine est connu ainsi que la distribution fréquentielle des caractères qui
le constituent.

Pour fonctionner les codes, il faut importer tous et insérer l'adress du fichier.bin et fichier_fre.txt puis les exécuter, vous pouvez obtenir le fichier decompressé dans la même racine que les codes.
