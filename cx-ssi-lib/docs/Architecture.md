# Architecture

**About arc42**

arc42, the template for documentation of software and system
architecture.

Template Version 8.2 EN. (based upon AsciiDoc version), January 2023

Created, maintained and © by Dr. Peter Hruschka, Dr. Gernot Starke and
contributors. See <https://arc42.org>.

## Introduction and Goals

The *SSI Agent Lib* (in the following referred to as **lib**) provides functions and concepts commonly required when implementing an digital wallet or other service that makes use of self-sovereign identites (SSI).

### Requirements Overview

The lib shall include features to support the following use cases and interactions:

| Feature                                    | Constraints                     | Details / Link                         |
|--------------------------------------------|---------------------------------|----------------------------------------|
| Create DID                                 |                                 | [link](FeatureSpec-CreateDid.md)       |
| Parse DID                                  |                                 | [link](FeatureSpec-ParseDid.md)        |
| Generate DID document                      |                                 | [link](FeatureSpec-GenerateDidDoc.md)  |
| Resolve DID document                       |                                 | [link](FeatureSpec-ResolveDidDoc.md)   |
| Create Verifiable Credential               | Limited to pre-defined schemas. | TBD                                    |
| Create Proof for Verifiable Credential     |                                 | [link](FeatureSpec-CreateSignedVC.md)  |
| Create Verifiable Presentation             |                                 | TBD                                    |
| Verify Verifiable Presentation             |                                 | TBD                                    |
| Validate Verifiable Presentation           |                                 | TBD                                    |
| Generate a key pair                        | Only Ed25519 supported.         | [link](FeatureSpec-GenerateKeyPair.md)|

### Quality Goals

| Priority | Quality Goal  | Scenario                                                      |
|----------|-------------- |---------------------------------------------------------------|
| 1        | Flexibility   | Multiple algorithms have to be supported.                     |
| 1        | Extensibility | Custom implementations can be integrated for certain aspects. |
| 2        | Usability     | The lib can be used and integrated easily in other systems.   |

## Architecture Constraints

**\<TDB>**

## System Scope and Context

**\<TDB>**

## Solution Strategy

## Building Block View

### Whitebox Overall System

***\<Overview Diagram>***

Motivation  
*\<text explanation>*

Contained Building Blocks  
*\<Description of contained building block (black boxes)>*

Important Interfaces  
*\<Description of important interfaces>*

#### \<Name black box 1>

*\<Purpose/Responsibility>*

*\<Interface(s)>*

*\<(Optional) Quality/Performance Characteristics>*

*\<(Optional) Directory/File Location>*

*\<(Optional) Fulfilled Requirements>*

*\<(optional) Open Issues/Problems/Risks>*

#### \<Name black box 2>

*\<black box template>*

#### \<Name black box n>

*\<black box template>*

#### \<Name interface 1>

…

#### \<Name interface m>

### Level 2

#### White Box *\<building block 1>*

*\<white box template>*

#### White Box *\<building block 2>*

*\<white box template>*

…

#### White Box *\<building block m>*

*\<white box template>*

### Level 3

#### White Box \<\_building block x.1\_\>

*\<white box template>*

#### White Box \<\_building block x.2\_\>

*\<white box template>*

#### White Box \<\_building block y.1\_\>

*\<white box template>*

## Runtime View

### \<Runtime Scenario 1>

-   *\<insert runtime diagram or textual description of the scenario>*

-   *\<insert description of the notable aspects of the interactions
    between the building block instances depicted in this diagram.>*

### \<Runtime Scenario 2>

### …

### \<Runtime Scenario n>

## Deployment View

### Infrastructure Level 1

***\<Overview Diagram>***

Motivation  
*\<explanation in text form>*

Quality and/or Performance Features  
*\<explanation in text form>*

Mapping of Building Blocks to Infrastructure  
*\<description of the mapping>*

### Infrastructure Level 2

#### *\<Infrastructure Element 1>*

*\<diagram + explanation>*

#### *\<Infrastructure Element 2>*

*\<diagram + explanation>*

…

#### *\<Infrastructure Element n>*

*\<diagram + explanation>*

## Cross-cutting Concepts

### *\<Concept 1>*

*\<explanation>*

### *\<Concept 2>*

*\<explanation>*

…

### *\<Concept n>*

*\<explanation>*

## Architecture Decisions

## Quality Requirements

### Quality Tree

### Quality Scenarios

## Risks and Technical Debts

## Glossary

| Term        | Definition        |
|-------------|-------------------|
| *\<Term-1>* | *\<definition-1>* |
| *\<Term-2>* | *\<definition-2>* |
