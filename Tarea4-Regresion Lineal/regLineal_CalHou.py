import numpy as np
import matplotlib.pyplot as plt
from sklearn.datasets import fetch_california_housing


# Cargar el dataset de California Housing
california = fetch_california_housing(as_frame=True)

# Seleccionar ingreso medio (X) y valor de la vivienda (Y) de las primeras 50 zonas
X = california.data['MedInc'].values[:50]
Y = california.target.values[:50]

print(f"Zonas analizadas: {len(X)}")
for xi, yi in zip(X[:5], Y[:5]):
    print(f"Ingreso: ${xi * 10000:,.0f} -> Precio: ${yi * 100000:,.0f}")

# Parámetros iniciales e hiperparámetros
w = 0.0
b = 0.0
learning_rate = 0.005
epochs = 3000
n = len(X)
historial_costos = []

# Entrenamiento mediante descenso de gradiente
for epoch in range(epochs):
    Y_pred = w * X + b
    
    # Error cuadrático medio
    cost = (1 / (2 * n)) * np.sum((Y_pred - Y) ** 2)
    historial_costos.append(cost)
    
    # Cálculo de gradientes y actualización de pesos
    dw = (2 / n) * np.sum((Y_pred - Y) * X)
    db = (2 / n) * np.sum(Y_pred - Y)
    
    w = w - learning_rate * dw
    b = b - learning_rate * db
    
    if epoch % 500 == 0 or epoch == epochs - 1:
        print(f"Época {epoch:>4} | Costo: {cost:.5f} | w: {w:.4f} | b: {b:.4f}")

print(f"\nEcuación obtenida: Y = {w:.4f}X + {b:.4f}")

# Comparación con mínimos cuadrados
w_exacto, b_exacto = np.polyfit(X, Y, 1)
print(f"Solución exacta:   w = {w_exacto:.4f}, b = {b_exacto:.4f}")

# Predicción de prueba
ingreso_prueba = 5.0
precio_estimado = w * ingreso_prueba + b
print(f"\nPredicción para ingreso de ${ingreso_prueba * 10000:,.0f}: ${precio_estimado * 100000:,.0f}")

# Gráfica de regresión lineal
plt.figure(figsize=(10, 6))
plt.scatter(X, Y, color='royalblue', s=60, label='Datos reales')

X_linea = np.linspace(min(X) - 0.5, max(X) + 0.5, 100)
Y_linea = w * X_linea + b
plt.plot(X_linea, Y_linea, color='crimson', linewidth=2.5, label=f'Regresión: Y = {w:.2f}X + {b:.2f}')

plt.title('Regresión Lineal: Ingreso Medio vs Valor de Vivienda')
plt.xlabel('Ingreso medio (decenas de miles de USD)')
plt.ylabel('Valor medio de la vivienda (cientos de miles de USD)')
plt.legend()
plt.grid(True, linestyle='--', alpha=0.6)
plt.show()

# Gráfica de convergencia del costo
plt.figure(figsize=(8, 4))
plt.plot(range(epochs), historial_costos, color='darkorange', linewidth=2)
plt.title('Evolución de la Función de Costo')
plt.xlabel('Épocas')
plt.ylabel('Costo')
plt.grid(True, linestyle='--', alpha=0.6)
plt.show()
