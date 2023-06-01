# Feature: Generate Key Pair

## 1. Specification

Given a supported key algorithm, generate a public / private key pair. 

*OPTIONAL*: 
- Generated keys *MAY* be returned as strings, encoded in a supported encoding.
- The seed used to initialize the random number generator *SHOULD* be returned.
- A seed *MAY* be specified to allow generating pseudo-random key pair (e.g. for testing purposes).

*Example:*
```json
{
    "type": "Ed25519VerificationKey2020",
    "publicKeyMultibase": "z6Mkqhx5Go6yU6yVt7vsWvu4QFPW5KMVGZmQASeiAdZ9ZmXL",
    "privateKeyMultibase": "zrv4DKJ9CLMzdmPanZmEi49nNMzj8MaHBH2CMfRQVdAr4FY1mpfex9qTGboUdmwvFA73zzzdqy6ycwXPrPELHQhdoCS"
}
```

#### 1.1 Assumptions
Multiple key algorithms *SHOULD* be supported.

#### 1.2 Constraints
Currently only verification type **Ed25519VerificationKey2020** needs to be supported.

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
