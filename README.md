# Nimble Docs

## Introduction

1. This project aims to automate all aspects of instrumenting application code running on Kubernetes with custom metrics and publishing them on prometheus.
2. Pod auto-scaling based on HPA and KEDA auto-scaler (TBA...)

## Table of Contents

1. **Observability**
       **Metrics:** [Metrics](./docs/metrics.md) document provides a description as well as demo of how applications can publish OpemMetrics compatible metrics to central prometheus and then to central monitoring.
2. **Auto-Scaling**
     **Pod Auto Scaling:** Pod auto-scaling can be of two types.(TBA...)
      1. [Hpa](./demos/hpa/) document describes how cpu/memory based autoscaling can be configured and managed.
      2. [KEDA](./docs/keda.md) document describes how auto-scaling can be configured based on complex custom metrics.
