package puzle;

public class AppPuzle {
    public static void main(String[] args) {
        String estadoInicial = "12345 678";
        String estadoFinal = " 12345678";

        ArbolPuzle arbol = new ArbolPuzle(new NodoPuzle(estadoInicial, null));

        System.out.println("=== 1. Búsqueda en Anchura (BFS) ===");
        NodoPuzle resultadoBFS = arbol.realizarBuquedaEnAnchura(estadoFinal);
        arbol.mostrarCamino(resultadoBFS);

        System.out.println("\n=== 2. Búsqueda en Profundidad (DFS) ===");
        NodoPuzle resultadoDFS = arbol.realizarBusquedaEnProfundidad(estadoFinal);
        arbol.mostrarCamino(resultadoDFS);

        System.out.println("\n=== 3. Búsqueda de Costo Uniforme (UCS) ===");
        NodoPuzle resultadoUCS = arbol.realizarBusquedaCostoUniforme(estadoFinal);
        arbol.mostrarCamino(resultadoUCS);

        System.out.println("\n=== 4. Búsqueda en Profundidad Iterativa (IDDFS) ===");
        NodoPuzle resultadoIDDFS = arbol.realizarBusquedaIterativa(estadoFinal, 50);
        arbol.mostrarCamino(resultadoIDDFS);
    }
}