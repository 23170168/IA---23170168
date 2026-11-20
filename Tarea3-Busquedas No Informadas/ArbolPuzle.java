package puzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class ArbolPuzle {
    NodoPuzle raiz;

    public ArbolPuzle(NodoPuzle raiz) {
        this.raiz = raiz;
    }

    // 1. Búsqueda Primero en Anchura (BFS)
    public NodoPuzle realizarBuquedaEnAnchura(String objetivo) {
        HashSet<String> visitados = new HashSet<String>();
        Queue<NodoPuzle> cola = new LinkedList<NodoPuzle>();
        cola.add(raiz);
        visitados.add(raiz.estado);
        boolean encontrado = false;
        NodoPuzle actual;

        while (!encontrado && cola.size() > 0) {
            actual = cola.poll();
            if (actual.estado.equals(objetivo)) {
                return actual;
            } else {
                List<String> sucesores = NodoPuzle.obtenerSucesores(actual.estado);
                for (String sucesor : sucesores) {
                    if (visitados.contains(sucesor)) {
                        continue;
                    }
                    cola.add(new NodoPuzle(sucesor, actual));
                    visitados.add(sucesor);
                }
            }
        }
        return null;
    }

    // 2. Búsqueda en Profundidad (DFS)
    public NodoPuzle realizarBusquedaEnProfundidad(String objetivo) {
        HashSet<String> visitados = new HashSet<>();
        Stack<NodoPuzle> pila = new Stack<>();
        pila.push(raiz);
        visitados.add(raiz.estado);

        while (!pila.isEmpty()) {
            NodoPuzle actual = pila.pop();

            if (actual.estado.equals(objetivo)) {
                return actual;
            }

            List<String> sucesores = NodoPuzle.obtenerSucesores(actual.estado);
            for (String sucesor : sucesores) {
                if (!visitados.contains(sucesor)) {
                    pila.push(new NodoPuzle(sucesor, actual));
                    visitados.add(sucesor);
                }
            }
        }
        return null;
    }

    // 3. Búsqueda de Costo Uniforme (UCS)
    public NodoPuzle realizarBusquedaCostoUniforme(String objetivo) {
        HashSet<String> visitados = new HashSet<>();
        PriorityQueue<NodoPuzle> cola = new PriorityQueue<>(
            (a, b) -> Integer.compare(a.costo, b.costo)
        );

        raiz.costo = 0;
        cola.add(raiz);

        while (!cola.isEmpty()) {
            NodoPuzle actual = cola.poll();

            if (actual.estado.equals(objetivo)) {
                return actual;
            }

            if (!visitados.contains(actual.estado)) {
                visitados.add(actual.estado);

                List<String> sucesores = NodoPuzle.obtenerSucesores(actual.estado);
                for (String sucesor : sucesores) {
                    if (!visitados.contains(sucesor)) {
                        NodoPuzle hijo = new NodoPuzle(sucesor, actual);
                        hijo.costo = actual.costo + 1;
                        cola.add(hijo);
                    }
                }
            }
        }
        return null;
    }

    // 4. Búsqueda en Profundidad Iterativa (IDDFS)
    public NodoPuzle realizarBusquedaIterativa(String objetivo, int limiteMaximo) {
        for (int limite = 0; limite <= limiteMaximo; limite++) {
            HashSet<String> caminoActual = new HashSet<>();
            NodoPuzle resultado = dfsLimitado(raiz, objetivo, limite, caminoActual);
            if (resultado != null) {
                return resultado;
            }
        }
        return null;
    }

    private NodoPuzle dfsLimitado(NodoPuzle nodo, String objetivo, int limite, HashSet<String> caminoActual) {
        if (nodo.estado.equals(objetivo)) {
            return nodo;
        }
        if (limite == 0) {
            return null;
        }
        caminoActual.add(nodo.estado);

        List<String> sucesores = NodoPuzle.obtenerSucesores(nodo.estado);
        for (String sucesor : sucesores) {
            if (!caminoActual.contains(sucesor)) {
                NodoPuzle hijo = new NodoPuzle(sucesor, nodo);
                NodoPuzle resultado = dfsLimitado(hijo, objetivo, limite - 1, caminoActual);
                if (resultado != null) {
                    return resultado;
                }
            }
        }
        caminoActual.remove(nodo.estado); // Backtracking para no bloquear rutas válidas
        return null;
    }

    // Heurística y Búsqueda A* (Opcional / Informada)
    private int heuristica(String estado, String objetivo) {
        int h = 0;
        for (int i = 0; i < estado.length(); i++) {
            char c = estado.charAt(i);
            if (c != ' ' && c != objetivo.charAt(i)) {
                h++;
            }
        }
        return h;
    }

    public NodoPuzle busquedaAEstrella(String objetivo) {
        PriorityQueue<NodoPuzle> cola = new PriorityQueue<>(
            Comparator.comparingInt(n -> (n.costo + heuristica(n.estado, objetivo)))
        );

        HashSet<String> visitados = new HashSet<>();
        cola.add(raiz);

        while (!cola.isEmpty()) {
            NodoPuzle actual = cola.poll();

            if (actual.estado.equals(objetivo)) {
                return actual;
            }

            visitados.add(actual.estado);

            for (String sucesor : NodoPuzle.obtenerSucesores(actual.estado)) {
                if (!visitados.contains(sucesor)) {
                    NodoPuzle hijo = new NodoPuzle(sucesor, actual);
                    hijo.costo = actual.costo + 1;
                    cola.add(hijo);
                }
            }
        }
        return null;
    }

    // Métodos auxiliares para imprimir la solución
    public void mostrarCamino(NodoPuzle solucion) {
        if (solucion == null) {
            System.out.println("No hay camino porque no se encontró solución.");
            return;
        }

        List<String> camino = new ArrayList<>();
        NodoPuzle actual = solucion;
        while (actual != null) {
            camino.add(actual.estado);
            actual = actual.padre;
        }
        Collections.reverse(camino);
        System.out.println();
        System.out.println("Movimientos (" + (camino.size() - 1) + " pasos):");
        for (String estado : camino) {
            imprimirTablero(estado);
            System.out.println("________");
        }
    }

    private void imprimirTablero(String estado) {
        for (int i = 0; i < estado.length(); i++) {
            char c = estado.charAt(i);
            System.out.print((c == ' ' ? "x" : c) + " ");
            if ((i + 1) % 3 == 0) {
                System.out.println();
            }
        }
    }
}