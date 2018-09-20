#!/bin/bash

# default values
input_file=""
output_dir=""
build_vonda="false"
post_process="true"

arg_count=0

function display_help() {
  echo "Usage: ./start.sh [OPTION]... INPUTFILE OUTPUT"
  echo "Convert INPUTFILE (in the format of sceneflow.xml) to rudi code stored in directory OUTPUT"
  echo
  echo "Options:"
  echo "  -b, --build-vonda          build VOnDA project"
  echo "  -n, --no-post-processing   don't post process dialog acts"
  exit 1
}

if [ $# -ge 2 ]; then
  while [ $# -gt 0 ]; do
    key="$1"

    case $key in
      -*) # key is option or flag
        case $key in
          -b|--build-vonda)
            build_vonda="true"
            shift
            ;;
          -n|--no-post-processing)
            post_process="false"
            shift
            ;;
          -bn|-nb)
            build_vonda="true"
            post_process="false"
            shift
            ;;
          *)
            display_help
            ;;
        esac
        ;;
      *) # key is a normal argument
        case $arg_count in
          0)
            input_file="$key"
            shift
            ;;
          1)
            output_dir="$key"
            shift
            ;;
          *)
            display_help
            ;;
        esac
        ((arg_count++))
        ;;
    esac
  done
else
  display_help
fi

echo java -jar target/scenemaker2vonda-1.0.jar "$input_file" "$output_dir" "$build_vonda" "$post_process"
java -jar target/scenemaker2vonda-1.0.jar "$input_file" "$output_dir" "$build_vonda" "$post_process"
