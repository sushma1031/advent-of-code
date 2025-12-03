#!/bin/bash

TEMPLATE_FILE="template.txt"
DIR="."

set -euo pipefail

# Parse command line arguments
while [[ $# -gt 0 ]]; do
    case $1 in
        -t|--template)
            TEMPLATE_FILE="$2"
            shift 2
            ;;
        -d|--root-dir)
            DIR="$2"
            shift 2
            ;;
        -h|--help)
            echo "Usage: $0 <day_number> <year> [options]"
            echo "  <day_number>            The day number (required)"
            echo "  <year>                  The year (required)"
            echo "Options:"
            echo "  -t, --template <file>   Template file to use (default: template.txt)"
            echo "  -d, --dir <path>        Path to where the templates should be generated (default: current working dir)"
            echo "  -h, --help              Show this message and exit"
            echo ""
            ;;
        -*)
            echo "Unknown option $1"
            exit 1
            ;;
        *)
            if [ -z "${DAY_NUM-}" ]; then
                DAY_NUM="$1"
            elif [ -z "${YEAR-}" ]; then
                YEAR="$1"
            else
                echo "Error: Unexpected argument '$1'"
                exit 1
            fi
            shift
            ;;
    esac
done

if [ -z "${DAY_NUM-}" ]; then
    echo "Error: Missing day number"
    exit 1
fi

if [ -z "${YEAR-}" ]; then
    echo "Error: Missing year"
    exit 1
fi

if ! [[ "${DAY_NUM}" =~ ^[1-9][0-9]*$ ]]; then
    echo "Error: Day number must be a positive integer"
    exit 1
fi

if [ ! -f "${TEMPLATE_FILE}" ]; then
    echo "Error: Template file '${TEMPLATE_FILE}' not found!"
    exit 1
fi

output_file="${DIR}/Day${DAY_NUM}.java"

# Replace placeholders in template and write to output file
sed -e "s/{{DAY_NUM}}/${DAY_NUM}/g" -e "s/{{YEAR}}/${YEAR}/g" "${TEMPLATE_FILE}" > "${output_file}"

echo "Generated ${output_file}"