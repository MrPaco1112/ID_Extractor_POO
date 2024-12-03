# ID_Extractor_POO

# Proyecto POO 2024

ID Extractor

- Automatiza y simplifica
  
# Paleta de colores:

![image](https://github.com/user-attachments/assets/68b15120-b4e8-4ecc-bc37-45e7ed56404d)

![image](https://github.com/user-attachments/assets/1db6192b-d2be-4f89-95bc-c304020223ca)

# Tipografías:

![image](https://github.com/user-attachments/assets/49397914-dcea-4861-a2ea-9fd3ed37a054)


# Logo:

![image](https://github.com/user-attachments/assets/80c8eb78-da6e-4ade-ad4e-681833533df7)

![image](https://github.com/user-attachments/assets/7bba7481-624e-4644-bd34-6e31af69e937)

# Mock-ups:

![Imagen de WhatsApp 2024-12-03 a las 13 23 09_0de5ffb2](https://github.com/user-attachments/assets/78a555c7-1712-4604-9dc4-2dfd0447fdd4) 

![Imagen de WhatsApp 2024-12-03 a las 13 23 09_938b7796](https://github.com/user-attachments/assets/0ecf32af-9153-4e75-a1ff-250481ebf84e)

![Imagen de WhatsApp 2024-12-03 a las 13 23 10_d87a2801](https://github.com/user-attachments/assets/37deff9d-64e0-4113-8dda-f9328008b1eb)

![Imagen de WhatsApp 2024-12-03 a las 13 23 10_5ef8259e](https://github.com/user-attachments/assets/355bf973-b5ad-4817-bf6c-3aaca039d1be)

# Diagrama de clases

![Diagrama de clases](https://github.com/user-attachments/assets/e5065a77-82c3-48fa-b6c9-32fbe0537dbc)

# Criterios de eleccion
 
Criterios de elección:
¿Para quién va dirigido?
"Nuestro proyecto no tiene un público objetivo definido, pero surge de la experiencia laboral de uno de los integrantes, orientada al manejo de datos relacionados con las EPS. Por su naturaleza, podría beneficiar a sectores como Recursos Humanos, instituciones gubernamentales o cualquier organización que requiera soluciones eficientes para gestionar información personal y biométrica."
¿Qué se planea transmitir?
Confianza, eficiencia, accesibilidad y tecnología. El diseño debe inspirar seguridad en el manejo de datos sensibles.

¿Qué contexto cultural debe reflejarse en el diseño?
De forma sutil alinearse con la cultura colombiana, incorporando elementos que reflejen el país. Además, debe reflejar modernidad para conectar con el contexto tecnológico.

¿En qué medios se usará el diseño?
Iconos de escritorio.
Documentación oficial (manuales, presentaciones, reportes).

¿Qué valores definen la identidad del proyecto?
Innovación: "Nuestro proyecto se destaca por su capacidad para transformar un proceso tradicionalmente manual y lento en una solución automatizada, ágil y precisa. Al integrar tecnologías avanzadas como el reconocimiento óptico de caracteres (OCR) y la gestión en la nube mediante Firebase, ofrecemos una herramienta que no solo reduce el tiempo y los recursos necesarios, sino que también incrementa la confiabilidad en el manejo de datos. Esta combinación de tecnologías y su enfoque en la eficiencia y seguridad permite posicionar el proyecto como una solución innovadora en el ámbito de la gestión de información personal y biométrica."
Eficiencia: Automatización y optimización del manejo de datos.
Seguridad: Cumplimiento de normativas de protección de datos.
Accesibilidad: Facilidad de uso para cualquier usuario.

¿Debe ser atemporal o seguir tendencias actuales?
- El diseño no necesariamente debe seguir tendencias actuales, ya que el enfoque principal del proyecto radica en su funcionalidad y adaptabilidad.

¿Qué colores y formas representan mejor el mensaje del proyecto?

Colores:

- Azul oscuro: Representa confianza, profesionalismo y tecnología.
- Blanco: Limpieza y accesibilidad.
- Toques de amarillo o dorado: Para reflejar innovación y conexión con la cultura colombiana.
- 
Formas:
- Líneas rectas y geométricas: Sugieren estabilidad y orden.
- Detalles circulares: Reflejan inclusividad y fluidez.

¿Qué nivel de detalle debe tener el diseño?
- Sencillo y minimalista, para garantizar claridad en diferentes escalas y contextos (íconos pequeños o banners grandes).

¿Cómo puede destacar frente a la competencia?
- Al reflejar profesionalismo y confianza tecnológica.
- Usando un diseño único que combina elementos nacionales con un estilo moderno.

# Flujo general

Pasos del Código (Alto Nivel Abstracto)

Inicio de la Aplicación

- La aplicación se inicia al hacer clic en un ícono en el escritorio o ejecutando un archivo .jar generado desde NetBeans.
- Se muestra una pantalla de inicio con el logo, slogan y un botón para comenzar el proceso.
  
Selección y Carga de Documentos

- Mostrar un cuadro de diálogo para que el usuario seleccione múltiples archivos PDF desde su equipo.
- Validar que los archivos seleccionados tengan el formato correcto (extensión .pdf) y almacenar sus rutas para su posterior procesamiento.
- Procesamiento de Datos Biográficos y Biométricos
- Leer el contenido de los documentos PDF seleccionados.
- Extraer información biográfica (nombre, número de cédula, fecha de nacimiento) y biométrica según corresponda.
- Consulta en la Página Web
- Automatizar el llenado de formularios en el sitio web de ADRES(https://www.adres.gov.co/consulte-su-eps#:~:text=De%20igual%20forma%20se%20pueden,.miseguridadsocial.gov.co).

Pasos principales:

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
  
Mostrar al usuario opciones para:

- Procesar más documentos desde el inicio (paso 2).
- Finalizar la aplicación cerrando las ventanas abiertas y liberando recursos.


