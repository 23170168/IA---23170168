import numpy as np

# Datos de entrenamiento para la compuerta lógica AND
# Entradas (X): combinaciones de [x1, x2]
X = np.array([
    [0, 0],
    [0, 1],
    [1, 0],
    [1, 1]
])

# Salidas esperadas (y): solo es 1 cuando ambas entradas son 1
y = np.array([0, 0, 0, 1])

# Inicialización de pesos, sesgo e hiperparámetros
pesos = np.zeros(X.shape[1])
sesgo = 0.0
tasa_aprendizaje = 0.1
epocas = 10

# Función de activación escalón
def escalon(z):
    return 1 if z >= 0 else 0

# Entrenamiento del perceptrón
for epoca in range(epocas):
    errores = 0
    for xi, objetivo in zip(X, y):
        # Suma ponderada + sesgo
        z = np.dot(xi, pesos) + sesgo
        prediccion = escalon(z)
        
        # Cálculo del error y ajuste de pesos
        error = objetivo - prediccion
        if error != 0:
            pesos += tasa_aprendizaje * error * xi
            sesgo += tasa_aprendizaje * error
            errores += 1
            
    print(f"Época {epoca + 1} | Errores: {errores} | Pesos: {pesos} | Sesgo: {sesgo:.2f}")
    if errores == 0:
        break

# Prueba del modelo entrenado
print("\nResultados finales (Compuerta AND):")
for xi in X:
    resultado = escalon(np.dot(xi, pesos) + sesgo)
    print(f"Entrada: {xi} -> Salida predicha: {resultado}")