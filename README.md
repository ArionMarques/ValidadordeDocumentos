# Document Validator (POC)

Proof of Concept (POC) for automated document validation using OCR.  
This project extracts text from images using **Tesseract OCR (via Tess4J)** and applies simple validation rules to estimate the reliability of submitted documents.

The goal is to evaluate the technical feasibility of automatically analyzing documents such as:

- Payslips (contracheques)
- Utility bills (água / energia)
- Rent receipts

This POC is part of an exploratory initiative to support **automated pre-validation of customer documents**.

---

## Overview

The application:

1. Receives an **image file** as input.
2. Uses **Tesseract OCR** to extract the text.
3. Applies a **validation strategy depending on the document type**.
4. Generates a **confidence score**.
5. Outputs the validation result to the console.
6. Saves the **raw OCR text to a `.txt` file** for inspection and debugging.

---

## Project Goals

This POC aims to answer the following questions:

- Can OCR reliably extract relevant information from common documents?
- Is it possible to create heuristic rules to estimate document reliability?
- What is the expected OCR accuracy on real-world inputs?

---

## Technologies Used

| Technology | Role |
|---|---|
| **Java 21** | Primary language |
| **Maven** | Build tool |
| **Tesseract OCR** | OCR engine |
| **Tess4J** | Java wrapper for Tesseract |
| **Apache PDFBox** | Transitive dependency |
| **Maven Shade Plugin** | Builds fat JAR |

---

## Project Structure

```
src/main/java/com/benoit/docvalidator
├── core
│   ├── DocumentType.java
│   ├── DocumentValidator.java
│   ├── ValidationResult.java
│   └── OcrTextSaver.java
├── ocr
│   └── OcrService.java
├── validator
│   ├── PayslipValidator.java
│   ├── UtilityBillValidator.java
│   └── RentReceiptValidator.java
└── Main.java
```

### Responsibilities

| Component | Responsibility |
|---|---|
| `OcrService` | Executes OCR using Tesseract |
| `OcrTextSaver` | Persists extracted text to `.txt` |
| `Validator` classes | Apply document-specific rules |
| `Main` | Orchestrates the pipeline |

---

## How It Works

Execution flow:

```
Input image
    ↓
OCR extraction (Tesseract)
    ↓
Raw text saved to TXT
    ↓
Document validation rules
    ↓
Confidence score
    ↓
Console output
```

Example output:

```
Status: MANUAL_REVIEW
Score: 40
Mensagem: Baixa confiabilidade
```

---

## Running the Application

### Build the project

```bash
mvn clean package
```

### Run

```bash
java -jar target/document-validator-1.0.0.jar <image-file> <document-type>
```

### Example

```bash
java -jar target/document-validator-1.0.0.jar payslip.png PAYSLIP
```

### Supported document types

- `PAYSLIP`
- `UTILITY_BILL`
- `RENT_RECEIPT`

---

## OCR Output

The extracted text is automatically saved in a `.txt` file in the same directory as the input document.

**Example:**

```
payslip.png       →  payslip_ocr.txt
```

This allows inspection of the raw OCR output to improve validation rules.

---

## Requirements

The project requires **Tesseract OCR** to be installed locally.

Example configuration in `OcrService`:

```
C:/dev/softwares/Tesseract-OCR/tessdata
```

Make sure the following file exists:

```
por.traineddata
```

---

## Current Limitations

- Focused primarily on image documents
- Validation logic is heuristic and keyword-based
- OCR quality depends on image quality and layout
- No structured field extraction yet (CPF, values, dates)

---

## Future Improvements

Possible next steps:

- Structured field extraction (CPF, CNPJ, values, dates)
- Confidence scoring per detected field
- REST API interface
- Frontend upload interface
- Document classification
- OCR confidence metrics

---

## Purpose

This project is intended as a **technical exploration** to evaluate the feasibility of automated document validation workflows.

> It is not intended for production use in its current form.
