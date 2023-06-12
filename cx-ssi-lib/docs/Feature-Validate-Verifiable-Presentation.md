# Feature: Validate Verifiable Presentation

## 1. Specification

Given a verifiable presentation, evaluate if the presention is valid.
The validity *MAY* be determined based on different criteria, e.g.
- expiration date
- expected audience

*Example: JWT payload*
```json
{
  "iss": "did:web:localhost%3A8080",
  "sub": "did:web:localhost%3A8080",
  "aud": "test",
  "vp": {
    "id": "did:web:localhost%3A8080#fd10a61d-3726-45e7-8355-db5f3a4dbe60",
    "type": [
      "VerifiablePresentation"
    ],
    "@context": [
      "https://www.w3.org/2018/credentials/v1"
    ],
    "verifiableCredential": {
      "@context": [
        "https://www.w3.org/2018/credentials/v1"
      ],
      "type": [
        "VerifiableCredential"
      ],
      "id": "https://localhost:8080/12345",
      "issuer": "did:web:localhost%3A8080",
      "issuanceDate": "2023-05-26T13:58:00Z",
      "expirationDate": "2000-01-23T04:56:07Z",
      "credentialSubject": {
        "name": "Jane Doe",
        "id": "did:example:abcdef1234567"
      },
      "proof": {
        "proofPurpose": "proofPurpose",
        "type": "Ed25519Signature2020",
        "proofValue": "zLLs4YXK4dhsaifJGmeyp23TsyUnGxJkobsT8fDgzXdq27dKFSgbXwvb857VyXRtBSLv2wBQbargrHJos93DreKT",
        "verificationMethod": "did:web:localhost%3A8080#key-1",
        "created": "2023-05-26T13:58:00Z"
      }
    }
  },
  "exp": 1686311279,
  "jti": "89c16630-69ca-4b18-baa7-e93d3d3a016c"
}
```

#### 1.1 Assumptions
Multiple signature algorithms *SHOULD* be supported.

#### 1.2 Constraints
Currently only verification type **Ed25519Signature2020** needs to be supported.

#### 1.3 System Environment
*none*

## 2. Architecture

#### 2.1 Overview
*Provide here a descriptive overview of the software/system/application architecture.*

#### 2.2 Component Diagrams
*Provide here the diagram and a detailed description of its most valuable parts. There may be multiple diagrams. Include a description for each diagram. Subsections can be used to list components and their descriptions.*

#### 2.3 Class Diagrams
*Provide here any class diagrams needed to illustrate the application. These can be ordered by which component they construct or contribute to. If there is any ambiguity in the diagram or if any piece needs more description provide it here as well in a subsection.*

#### 2.4 Sequence Diagrams
*Provide here any sequence diagrams. If possible list the use case they contribute to or solve. Provide descriptions if possible.*

#### 2.5 Deployment Diagrams
*Provide here the deployment diagram for the system including any information needed to describe it. Also, include any information needed to describe future scaling of the system.*

#### 2.6 Other Diagrams
*Provide here any additional diagrams and their descriptions in subsections.*

## 3 User Interface Design
*Provide here any user interface mock-ups or templates. Include explanations to describe the screen flow or progression.*

## 4 Appendices and References


#### 4.1 Definitions and Abbreviations
*List here any definitions or abbreviations that could be used to help a new team member understand any jargon that is frequently referenced in the design document.*

#### 4.2 References
*List here any references that can be used to give extra information on a topic found in the design document. These references can be referred to using superscript in the rest of the document.*
