# ID Manager

INDICE
- Diseño  
- Diagrama de clases
- Criterios de eleccion
- Paso a Paso instalacion
- Flujo general
- Apariencia final




![image](https://github.com/user-attachments/assets/eeb6c74c-0973-4c45-bed9-9053b46655bf)
---

# Diseño  
## Paleta de colores:

![image](https://github.com/user-attachments/assets/68b15120-b4e8-4ecc-bc37-45e7ed56404d)

![image](https://github.com/user-attachments/assets/1db6192b-d2be-4f89-95bc-c304020223ca)

## Tipografías:

![image](https://github.com/user-attachments/assets/49397914-dcea-4861-a2ea-9fd3ed37a054)


## Logo:

![image](https://github.com/user-attachments/assets/80c8eb78-da6e-4ade-ad4e-681833533df7)

![image](https://github.com/user-attachments/assets/7bba7481-624e-4644-bd34-6e31af69e937)

### Color logo
Se modificaron los colores iniciales para hacerlo mas llamativo
![image](https://github.com/user-attachments/assets/7cac42e9-3703-4add-9f8d-0d5b93e272c2)
![image](https://github.com/user-attachments/assets/b472c875-a1fc-47b9-a37d-a653c03e20fb)

### Test logo 
Se testearon distintos logos con el fin de tener el mayor contraste en tema oscuro y claro
![image](https://github.com/user-attachments/assets/2b4a3250-510b-4217-a16a-e000c23a4bc5)
![image](https://github.com/user-attachments/assets/e09421d6-dd89-44d8-87d7-2fc31682a10e)




## Mock-ups:

![image](https://github.com/user-attachments/assets/e9dd40fc-ee85-4032-99a4-cb47474c8a7b)

![image](https://github.com/user-attachments/assets/510104f6-244b-4969-a416-fcb7b6b2854f)

![image](https://github.com/user-attachments/assets/1d284bf6-43f8-4a36-bb6e-5ac63aef929b)

![image](https://github.com/user-attachments/assets/fdf65005-5cb1-4691-9ec6-757def06d389)

![image](https://github.com/user-attachments/assets/1a2ca1c7-85e6-458c-8125-67b664cd499c)

![image](https://github.com/user-attachments/assets/abef5e3e-c460-4f15-b7c2-b2846b1e08b3)

![image](https://github.com/user-attachments/assets/aac31c6d-3162-4149-8cea-d55ef9c63bd1)

![image](https://github.com/user-attachments/assets/bae0ee63-4d97-48a2-ad5d-4c05af9cfdfb)

![image](https://github.com/user-attachments/assets/b3d6fde3-08a7-4fef-8884-cfa71d86334b)

---
---

# Diagrama de clases inicial

![Diagrama de clases (1)](https://github.com/user-attachments/assets/89396852-b111-4f92-ab93-1c839ed69f48)

---
---

# Criterios de eleccion
 
Criterios de elección del proyecto:

¿Para quién va dirigido?

"Nuestro proyecto surge de la experiencia laboral de uno de los integrantes, orientada al manejo de datos relacionados con las EPS. Por su naturaleza, podría beneficiar a sectores como Recursos Humanos, instituciones gubernamentales o cualquier organización que requiera soluciones eficientes para gestionar información personal y biométrica."

¿Qué se planea transmitir?

Confianza, eficiencia, accesibilidad y tecnología. El diseño debe inspirar seguridad en el manejo de datos sensibles.


¿En qué medios se usará el diseño?

Iconos de escritorio.

Documentación oficial (manuales, presentaciones, reportes).

¿Qué valores definen la identidad del proyecto?

Innovación:

"Nuestro proyecto se destaca por su capacidad para transformar un proceso tradicionalmente manual y lento en una solución automatizada, ágil y precisa. Al integrar tecnologías avanzadas como el reconocimiento óptico de caracteres (OCR) y la gestión en la nube mediante Firebase o Excel, ofrecemos una herramienta que no solo reduce el tiempo y los recursos necesarios, sino que también incrementa la confiabilidad en el manejo de datos. Esta combinación de tecnologías y su enfoque en la eficiencia y seguridad permite posicionar el proyecto como una solución innovadora en el ámbito de la gestión de información personal y biométrica."

- Eficiencia: Automatización y optimización del manejo de datos.

- Seguridad: Cumplimiento de normativas de protección de datos.

- Accesibilidad: Facilidad de uso para cualquier usuario.

¿Debe ser atemporal o seguir tendencias actuales?
- El diseño no necesariamente debe seguir tendencias actuales, ya que el enfoque principal del proyecto radica en su funcionalidad y adaptabilidad.

¿Qué colores y formas representan mejor el mensaje del proyecto?

Colores:

- Azul oscuro: Representa confianza, profesionalismo y tecnología.
- Blanco: Limpieza y accesibilidad.


Formas:
- Líneas rectas y geométricas: Sugieren estabilidad y orden.
- Detalles circulares: Reflejan inclusividad y fluidez.

¿Qué nivel de detalle debe tener el diseño?
- Sencillo y minimalista, para garantizar claridad en diferentes escalas y contextos (íconos pequeños o banners grandes).

¿Cómo puede destacar frente a la competencia?
- Al reflejar profesionalismo y confianza tecnológica.
- Usando un diseño único que combina elementos nacionales con un estilo moderno.

---
---

# Paso a Paso instalacion

https://drive.google.com/drive/folders/1nmum3sRmDNa3wkcc8pE1dRI-mNJXz1uN?usp=sharing

## 1. Descarga del Proyecto
Descargar el archivo ZIP del proyecto desde NetBeans.

Descomprimir el archivo y ubicar la carpeta en una ubicación de preferencia.

## 2. Descarga del Instalador de Tesseract

Descargar el instalador de Tesseract desde Google Drive.

## 3. Instalación de Tesseract

Ejecutar el instalador y seguir el proceso de instalación estándar.

Importante: Tomar nota de la ubicación donde se guardará el ejecutable de Tesseract.

## 4. Configuración del Archivo OCR.py
Abrir la consola de comandos y ejecutar el siguiente comando para instalar las dependencias necesarias: pip install opencv-python numpy pillow pytesseract

Acceder a la siguiente ruta del proyecto: IdManager/src/main/main/resources/

Abrir el archivo OCR.py con IDLE o un editor de código.

Debajo de las importaciones, añadir la siguiente línea de código, reemplazando con la ruta correcta del ejecutable de Tesseract: pytesseract.pytesseract.tesseract_cmd = r'C:\Camino\de\ejemplo\Tesseract-OCR\tesseract.exe'

Con esta configuración, la funcionalidad OCR debería operar correctamente.

## 5. Instalación y Configuración de JSON
Descargar los archivos JSON desde Google Drive.

Mover los archivos JSON a la siguiente ruta del proyecto: IdManager/src/main/main/resources/

Con esta configuración, la inicialización de Firebase y el funcionamiento del programa deberían completarse correctamente.

## 6. Ejecución desde NetBeans
Abrir NetBeans y cargar la carpeta del proyecto desde el explorador de archivos.

Ejecutar el proyecto desde NetBeans utilizando el botón de ejecución (triángulo en la parte superior) o presionando F6.

---
---

# Flujo general

Pasos del Código (Alto Nivel Abstracto)

## nicio de la Aplicación

- La aplicación se inicia al hacer clic en un ícono en el escritorio o ejecutando un archivo .jar generado desde NetBeans.
- Se muestra una pantalla de inicio con el logo, slogan y un botón para comenzar el proceso.
  
## Selección y Carga de Documentos

- Mostrar un cuadro de diálogo para que el usuario seleccione múltiples archivos PDF desde su equipo.
- Validar que los archivos seleccionados tengan el formato correcto (extensión .pdf) y almacenar sus rutas para su posterior procesamiento.
- Procesamiento de Datos Biográficos y Biométricos
- Leer el contenido de los documentos PDF seleccionados.
- Extraer información biográfica (nombre, número de cédula, fecha de nacimiento) y biométrica según corresponda.
- Consulta en la Página Web
- Automatizar el llenado de formularios en el sitio web de ADRES(https://www.adres.gov.co/consulte-su-eps#:~:text=De%20igual%20forma%20se%20pueden,.miseguridadsocial.gov.co).

## Pasos principales:

- Navegar al sitio web.
- Completar los campos de tipo de documento y número de cédula.
- Capturar y procesar el CAPTCHA con ayuda de una librería OCR si es necesario.
- Capturar la información resultante (e.g., nombre de la EPS) para su integración.
- Registro de Datos y Generación de Excel
- Guardar los datos procesados en un archivo Excel utilizando la librería Apache POI.
- Subida a Firebase
- Utilizar el SDK de Firebase para subir los archivos Excel generados a Firebase Storage.
- Registrar la ubicación de los archivos en Firestore o Realtime Database para su seguimiento.
- Reinicio del Proceso
  
## Mostrar al usuario opciones para:

- Procesar más documentos desde el inicio (paso 2).
- Finalizar la aplicación cerrando las ventanas abiertas y liberando recursos.

---
---

# Apariencia final

## Ventana login
![image](https://github.com/user-attachments/assets/a42f788e-3e00-48d7-b9ba-e1f7e8e8c473)

## Selector de Directorio local
![image](https://github.com/user-attachments/assets/ca27dabf-96b3-4210-adbb-b8dcc9ee7a2c)

## Ventana principal, Vista de bienvenida
![image](https://github.com/user-attachments/assets/ab2b4c36-4f80-4b0a-9022-30e01b8c264b)

## Ventana principal, Vista de Empresa Seleccionada "UNAL"
![image](https://github.com/user-attachments/assets/1e08a0d0-3273-4b25-a977-66bf1abf814c)

## Ventana del resultado del escaner
![image](https://github.com/user-attachments/assets/710c8aa4-bf1c-48d7-8868-f2de566750a9)







