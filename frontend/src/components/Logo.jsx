import {createIcon, Link} from "@chakra-ui/react";
import React from "react";

const LogoIcon = createIcon({
    displayName: 'Logo',
    viewBox: '0 0 250 200',
    // path can also be an array of elements, if you have multiple paths, lines, shapes, etc.
    path: (
        <g id="Layer_2" data-name="Layer 2" fill="currentColor">
            <g id="Layer_1-2" data-name="Layer 1">
                <polygon className="cls-1"
                         points="180.27 87.64 147.29 87.64 163.53 111.14 164.31 112.27 183.32 139.79 56.71 139.79 5.77 166.93 235.01 166.93 180.27 87.64"/>
                <polygon className="cls-1"
                         points="180.27 87.64 147.29 87.64 133.46 67.62 133.43 67.64 119.87 47.86 119.87 0.03 152.13 46.95 152.16 46.92 180.27 87.64"/>
                <polygon className="cls-1" points="164.66 112.27 81.21 112.27 97.37 87.64 147.29 87.64 164.66 112.27"/>
                <path className="cls-2" d="M82.36,37.16,108,0V47.8l-6.64,9.63C95.37,51.08,88.27,43.5,82.36,37.16Z"/>
                <path fill="currentColor" className="cls-2"
                      d="M74.91,47.75,0,156.54l52.11-27.76,12.14-17.64,16.16-23.5L93.9,68C87.92,61.67,80.81,54.1,74.91,47.75Z"/>
                <polygon className="cls-2" points="59.77 87.64 80.41 87.64 64.25 111.14 59.77 111.14 59.77 87.64"/>
                <polygon className="cls-1" points="180.27 87.64 180.27 111.14 163.53 111.14 147.29 87.64 180.27 87.64"/>
            </g>
        </g>
    ),
})

export default function Logo() {
    return (
        <LogoIcon/>
    )
}