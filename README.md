# Page-Configurator
This web application allows users to upload a PDF file, view thumbnail previews of each page, and select whether each page should be printed in Color or Black &amp; White.  After selection, the app displays a clear print summary showing which pages are assigned to each mode, and provides copy buttons to quickly copy the page lists.

# 📄 PDF Page Mode Selector & Print Preview

A simple Spring Boot web application that allows users to upload a PDF file, preview pages as thumbnails, and select print modes (Color or Black & White) for each page. The app then generates a summary of selections to assist with optimized print configuration.

---

## 🔧 Features

- 📤 **Upload PDF**: Users can upload a PDF file for preview and selection.
- 🖼️ **Preview Pages**: PDF pages are displayed as thumbnails for easy navigation.
- 🎨 **Page Mode Selection**: Choose whether each page should be printed in *Color* or *Black & White*.
- 📋 **Print Summary**: A summary section displays selected pages for each mode, with copy buttons for quick access.
- ✅ **No Database Required**: Everything runs in-memory for simplicity.
- 💡 **Built With**:
  - Java 17+
  - Spring Boot
  - Thymeleaf
  - Bootstrap 5
  - Apache PDFBox

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/SureshB2938/Page-Configurator/
cd Page-Configuator

Here's the corrected section with proper formatting in Markdown for your README:

````markdown
## 🚀 Build & Run the Application

Make sure you have **Java 17+** and **Maven** installed on your system.

### 🛠️ Steps to Run:

```bash
mvn clean install
mvn spring-boot:run
````

Once the app starts, open your browser and go to:

```
http://localhost:8080
```



