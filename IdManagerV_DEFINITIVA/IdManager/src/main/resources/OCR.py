import os
import cv2
import numpy as np
from PIL import Image
import pytesseract
pytesseract.pytesseract.tesseract_cmd = r'C:\Program Files\Tesseract-OCR\tesseract.exe'
def remove_white_background(image, threshold=220):
    image_bgr = image.copy()
    image_rgba = cv2.cvtColor(image_bgr, cv2.COLOR_BGR2BGRA)
    gray = cv2.cvtColor(image_bgr, cv2.COLOR_BGR2GRAY)
    _, mask = cv2.threshold(gray, threshold, 255, cv2.THRESH_BINARY)
    image_rgba[mask == 255, 3] = 0
    return image_rgba

def remove_transparency_and_crop(rgba_image):
    if rgba_image.shape[2] != 4:
        raise ValueError("La imagen no tiene 4 canales (RGBA).")
    alpha_channel = rgba_image[:, :, 3]
    mask = alpha_channel != 0
    coords = np.argwhere(mask)
    if coords.size == 0:
        raise ValueError("Toda la imagen es transparente. Nada que recortar.")
    y_min, x_min = coords.min(axis=0)
    y_max, x_max = coords.max(axis=0)
    cropped_rgba = rgba_image[y_min:y_max+1, x_min:x_max+1, :]
    cropped_bgr = cv2.cvtColor(cropped_rgba, cv2.COLOR_BGRA2BGR)
    return cropped_bgr

def crop_full_image(image):
    height, width = image.shape[:2]
    left = int((3/8) * width)
    upper = 0
    right = width
    lower = int((11.5/20) * height)
    return image[upper:lower, left:right]

def process_image(image):
    gray_image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    lower_gray = 135
    upper_gray = 255
    mask = (gray_image >= lower_gray) & (gray_image <= upper_gray)
    gray_image[mask] = 255
    return gray_image

def crop_field(pil_image, field):
    width, height = pil_image.size
    if field == "ID":
        left = int(0.61 * width)
        upper = int(0.2 * height)
        right = width
        lower = int(0.5 * height)
    elif field == "Apellidos":
        left = 0
        upper = int(0.3 * height)
        right = int(0.5 * width)
        lower = int(0.5 * height)
    elif field == "Nombre":
        left = 0
        upper = int(0.54 * height)
        right = int(0.5 * width)
        lower = int(0.7 * height)
    elif field == "Fecha de nacimiento":
        left = 0
        upper = int(0.9 * height)
        right = int(0.32 * width)
        lower = height
    else:
        raise ValueError("Campo desconocido")
    
    return pil_image.crop((left, upper, right, lower))

def extract_text(pil_image, config=r'--oem 3 --psm 6'):
    return pytesseract.image_to_string(pil_image, config=config).strip()

def main():
    # Calcula la ruta absoluta de la carpeta donde está OCR.py
    script_dir = os.path.dirname(os.path.abspath(__file__))
    
    # Construye la ruta a la imagen ccFondoBlanco.jpg asumiendo que está
    # en la misma carpeta que OCR.py (carpeta 'resources')
    input_path = os.path.join(script_dir, "ccFondoBlanco.jpg")
    
    original_image = cv2.imread(input_path)
    if original_image is None:
        print(f"Error: No se pudo cargar la imagen: {input_path}")
        return
    
    image_rgba = remove_white_background(original_image, threshold=220)
    image_bgr = remove_transparency_and_crop(image_rgba)
    full_cropped = crop_full_image(image_bgr)
    processed = process_image(full_cropped)
    
    pil_image = Image.fromarray(processed)
    campos = ["ID", "Apellidos", "Nombre", "Fecha de nacimiento"]
    resultados = {}
    for campo in campos:
        recorte = crop_field(pil_image, campo)
        texto = extract_text(recorte)
        resultados[campo] = texto
    
    print("Datos extraídos:")
    for campo, texto in resultados.items():
        print(f"{campo}: {texto}")

if __name__ == "__main__":
    main()
